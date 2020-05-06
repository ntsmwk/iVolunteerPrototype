import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { ClassInstanceDTO, ClassInstance } from 'app/main/content/_model/meta/class';
import { LoginService } from 'app/main/content/_service/login.service';
import { Participant, ParticipantRole } from 'app/main/content/_model/participant';
import { CoreVolunteerService } from 'app/main/content/_service/core-volunteer.service';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { MatTableDataSource } from '@angular/material';


@Component({
  selector: 'app-class-instance-details',
  templateUrl: './class-instance-details.component.html',
  styleUrls: ['./class-instance-details.component.scss']
})
export class ClassInstanceDetailsComponent implements OnInit {

  id: string;
  tenantId: string;
  classInstance: ClassInstance;
  participant: Participant;
  role: ParticipantRole;
  marketplace: Marketplace;

  tableDataSource = new MatTableDataSource();
  displayedColumns = ['name', 'values', 'type'];


  constructor(
    private route: ActivatedRoute,
    private classInstanceService: ClassInstanceService,
    private loginService: LoginService,
    private volunteerService: CoreVolunteerService,
    private helpseekerService: CoreHelpSeekerService,


  ) {
    this.route.params.subscribe(params => {
      this.id = params.id;
      this.tenantId = params.tenantId;
    });
  }

  async ngOnInit() {
    //this.classInstance = <ClassInstanceDTO>history.state.data.classInstance;

    this.participant = <Participant>await this.loginService.getLoggedIn().toPromise();

    this.role = <ParticipantRole>await this.loginService.getLoggedInParticipantRole().toPromise();

    let marketplaces = [];
    if (this.role === 'HELP_SEEKER') {
      marketplaces = <Marketplace[]>(
        await this.helpseekerService.findRegisteredMarketplaces(this.participant.id).toPromise()
      );
    } else if (this.role === 'VOLUNTEER') {
      marketplaces = <Marketplace[]>(
        await this.volunteerService.findRegisteredMarketplaces(this.participant.id).toPromise()
      );
    }
    this.marketplace = marketplaces[0];

    this.classInstance = <ClassInstance>(
      await this.classInstanceService.getClassInstanceById(this.marketplace, this.id, this.tenantId).toPromise()
    );

    console.error(this.classInstance);
    this.tableDataSource.data = this.classInstance.properties;




  }

  navigateBack() {
    window.history.back();
  }

}

