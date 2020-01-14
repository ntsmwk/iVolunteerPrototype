import { Component, OnInit } from '@angular/core';
import { isNullOrUndefined } from 'util';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from '../_service/login.service';
import { Participant } from '../_model/participant';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { Marketplace } from '../_model/marketplace';
import { ClassArchetype, ClassDefinition } from '../_model/meta/Class';
import { ClassDefinitionService } from '../_service/meta/core/class/class-definition.service';
import { MatTableDataSource } from '@angular/material';

@Component({
  templateUrl: './task-select.component.html',
  styleUrls: ['./task-select.component.scss'],
})
export class FuseTaskSelectComponent implements OnInit {

  marketplace: Marketplace;
  dataSource = new MatTableDataSource<ClassDefinition>();
  displayedColumns = ['name'];

  constructor(formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService,
    private coreHelpSeekerService: CoreHelpSeekerService,
    private classDefinitionService: ClassDefinitionService) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.coreHelpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.marketplace = marketplace;
          this.classDefinitionService.getByArchetype(marketplace, ClassArchetype.TASK).toPromise().then((tasks: ClassDefinition[]) => {
            this.dataSource.data = tasks;
          });
        }
      });
    });
  }

  onRowSelect(row) {
    console.error(row);
  }

}