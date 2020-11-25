import { Component, OnInit } from "@angular/core";
import { MatTableDataSource } from "@angular/material";
import { FormBuilder } from "@angular/forms";
import { Router, ActivatedRoute } from "@angular/router";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { isNullOrUndefined } from "util";
import {
  ClassDefinition,
  ClassArchetype
} from "../../_model/configurator/class";
import { environment } from "environments/environment";

@Component({
  templateUrl: "./task-select.component.html",
  styleUrls: ["./task-select.component.scss"]
})
export class FuseTaskSelectComponent implements OnInit {
  allClassDefinitions: ClassDefinition[];
  dataSource = new MatTableDataSource<ClassDefinition>();
  displayedColumns = ["name", "configuration"];
  tenantId: string;
  redirectUrl: string;
  environmentMode: string;
  dropdownFilterValue: string;

  constructor(
    formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private classDefinitionService: ClassDefinitionService
  ) { }

  async ngOnInit() {
    this.environmentMode = environment.MODE;


    this.route.queryParams.subscribe(params => {
      if (
        isNullOrUndefined(params["tenantId"]) ||
        isNullOrUndefined(params["redirect"])
      ) {
        this.router.navigate(["main/invalid-parameters"]);
      } else {
        this.tenantId = params["tenantId"];
        this.redirectUrl = params["redirect"];
      }
    });

    let archetypes: ClassArchetype[] = [];
    if (environment.MODE === 'flexprod') {
      archetypes = [ClassArchetype.FLEXPROD]
    } else if (environment.MODE === 'iVolunteer') {
      archetypes = [ClassArchetype.TASK, ClassArchetype.FUNCTION, ClassArchetype.ACHIEVEMENT, ClassArchetype.COMPETENCE];
    } else {
      archetypes = [ClassArchetype.TASK, ClassArchetype.FUNCTION, ClassArchetype.ACHIEVEMENT, ClassArchetype.COMPETENCE, ClassArchetype.FLEXPROD];
    }


    this.allClassDefinitions = <ClassDefinition[]>await this.classDefinitionService
      .getByArchetypes(archetypes, this.tenantId).toPromise();
    this.dropdownFilterValue = 'ALL';


    this.dataSource.data = this.allClassDefinitions
      .filter(t => t.configurationId != null)
      .sort((c1, c2) => c1.configurationId.localeCompare(c2.configurationId));
  }

  applyArchetypeFilter() {
    if (this.dropdownFilterValue === 'ALL') {
      this.dataSource.data = this.allClassDefinitions;
    } else {
      this.dataSource.data = this.allClassDefinitions.filter(cd => cd.classArchetype === this.dropdownFilterValue);
    }

  }

  onRowSelect(row) {
    this.router.navigate([`main/instance-editor`], {
      queryParams: {
        cdId: row.id,
        tenantId: this.tenantId,
        redirect: this.redirectUrl
      }
    });
  }
}
