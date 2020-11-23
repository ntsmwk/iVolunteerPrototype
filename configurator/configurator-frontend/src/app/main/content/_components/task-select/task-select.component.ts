import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { isNullOrUndefined } from 'util';
import { ClassDefinition, ClassArchetype } from '../../_model/configurator/class';

@Component({
  templateUrl: './task-select.component.html',
  styleUrls: ['./task-select.component.scss']
})
export class FuseTaskSelectComponent implements OnInit {
  dataSource = new MatTableDataSource<ClassDefinition>();
  displayedColumns = ['name', 'configuration'];
  tenantId: string;
  redirectUrl: string;

  constructor(
    formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private classDefinitionService: ClassDefinitionService
  ) { }

  async ngOnInit() {
    this.route.queryParams.subscribe(params => {
      if (isNullOrUndefined(params['tenantId']) || isNullOrUndefined(params['redirect'])) {
        this.router.navigate(['main/invalid-parameters']);
      } else {
        this.tenantId = params['tenantId'];
        this.redirectUrl = params['redirect'];
      }
    });
    const tasks = <ClassDefinition[]>await this.classDefinitionService.getByArchetype(ClassArchetype.TASK, this.tenantId).toPromise();

    this.dataSource.data = tasks
      .filter(t => t.configurationId != null)
      .sort((c1, c2) => c1.configurationId.localeCompare(c2.configurationId));

  }

  onRowSelect(row) {
    this.router.navigate([`main/instance-editor`], {
      queryParams: { cdId: row.id, tenantId: this.tenantId, redirect: this.redirectUrl }
    });
  }
}
