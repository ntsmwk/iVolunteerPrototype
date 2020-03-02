import { Component, OnInit } from '@angular/core';
import { isNullOrUndefined } from 'util';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from '../../../_service/login.service';
import { Participant } from '../../../_model/participant';
import { CoreHelpSeekerService } from '../../../_service/core-helpseeker.service';
import { Marketplace } from '../../../_model/marketplace';
import { ClassArchetype, ClassDefinition } from '../../../_model/meta/Class';
import { ClassDefinitionService } from '../../../_service/meta/core/class/class-definition.service';
import { MatTableDataSource } from '@angular/material';
import { Helpseeker } from '../../../_model/helpseeker';

@Component({
  templateUrl: './task-select.component.html',
  styleUrls: ['./task-select.component.scss'],
})
export class FuseTaskSelectComponent implements OnInit {

  marketplace: Marketplace;
  dataSource = new MatTableDataSource<ClassDefinition>();
  displayedColumns = ['name'];
  helpseeker: Helpseeker;

  constructor(formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService,
    private coreHelpSeekerService: CoreHelpSeekerService,
    private classDefinitionService: ClassDefinitionService) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.helpseeker = helpseeker;
      this.coreHelpSeekerService.findRegisteredMarketplaces(helpseeker.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.marketplace = marketplace;
          this.classDefinitionService.getByArchetype(marketplace, ClassArchetype.TASK, this.helpseeker.tenantId).toPromise().then((tasks: ClassDefinition[]) => {
            this.dataSource.data = tasks.filter(t => t.name != 'PersonTask');
          });
        }
      });
    });
  }

  onRowSelect(row) {
    this.router.navigate([`main/configurator/instance-editor/${this.marketplace.id}`], { queryParams: { 0: row.id } });
  }

  private isFF() {
    return this.helpseeker.username == 'FFA';
  }

  private isMV() {
    return this.helpseeker.username === 'MVS';
  }
  private isOther() {
    return !this.isFF() && !this.isMV();
  }


}