import {Component, OnInit} from '@angular/core';
import {isNullOrUndefined} from 'util';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {LoginService} from '../_service/login.service';
import {Participant} from '../_model/participant';
import {CoreEmployeeService} from '../_service/core-employee.service';
import {Marketplace} from '../_model/marketplace';
import {ProjectService} from '../_service/project.service';
import {Project} from '../_model/project';

@Component({
  templateUrl: './project-form.component.html',
  styleUrls: ['./project-form.component.scss']
})
export class FuseProjectFormComponent implements OnInit {
  projectForm: FormGroup;

  constructor(formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private loginService: LoginService,
              private coreEmployeeService: CoreEmployeeService,
              private projectService: ProjectService) {
    this.projectForm = formBuilder.group({
      'id': new FormControl(undefined),
      'name': new FormControl(undefined),
      'description': new FormControl(undefined),
      'startDate': new FormControl(undefined, Validators.required),
      'endDate': new FormControl(undefined),
    });
  }


  isEditMode() {
    return !isNullOrUndefined(this.projectForm.value.id);
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.coreEmployeeService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        this.route.params.subscribe(params => this.findProject(marketplace, params['projectId']));
      });
    });
  }

  private findProject(marketplace: Marketplace, projectId: string) {
    if (isNullOrUndefined(projectId) || projectId.length === 0) {
      return;
    }

    this.projectService.findById(marketplace, projectId).toPromise().then((project: Project) => {
      this.projectForm.setValue({
        id: project.id,
        name: project.name,
        description: project.description,
        startDate: new Date(project.startDate),
        endDate: new Date(project.endDate)
      });
    });
  }

  save() {
    if (!this.projectForm.valid) {
      return;
    }

    const project = <Project>this.projectForm.value;
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.coreEmployeeService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        this.projectService.save(marketplace, project).toPromise().then(() => this.router.navigate(['/main/projects/all']));
      });
    });
  }
}
