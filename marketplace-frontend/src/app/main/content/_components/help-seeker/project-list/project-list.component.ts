import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { LoginService } from '../../../_service/login.service';
import { CoreHelpSeekerService } from '../../../_service/core-helpseeker.service';
import { Participant } from '../../../_model/participant';
import { Marketplace } from '../../../_model/marketplace';
import { Project } from '../../../_model/project';
import { ProjectService } from '../../../_service/project.service';
import { fuseAnimations } from '@fuse/animations';
import { Router } from '@angular/router';
import { isNullOrUndefined } from 'util';

@Component({
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.scss'],
  encapsulation: ViewEncapsulation.None,
  animations: fuseAnimations
})
export class FuseProjectListComponent implements OnInit {

  dataSource = new MatTableDataSource<Project>();
  displayedColumns = ['name', 'description', 'startDate', 'endDate', 'actions'];

  constructor(private loginService: LoginService,
    private coreHelpSeekerService: CoreHelpSeekerService,
    private projectService: ProjectService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
      this.coreHelpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.projectService.findAll(marketplace)
            .toPromise()
            .then((projects: Project[]) => this.dataSource.data = projects);
        }
      });
    });
  }

  addProject() {
    this.router.navigate(['/main/project-form'])
  }
}
