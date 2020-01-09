import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../_model/marketplace';
import { ClassDefinitionService } from '../../../_service/meta/core/class/class-definition.service';
import { ClassInstance } from '../../../_model/meta/Class';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { QuestionService } from 'app/main/content/_service/question.service';
import { FormConfiguration, FormEntryReturnEventData } from 'app/main/content/_model/meta/form';
import { QuestionControlService } from 'app/main/content/_service/question-control.service';
import { PropertyInstance } from 'app/main/content/_model/meta/Property';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { isNullOrUndefined } from 'util';
import { Volunteer } from 'app/main/content/_model/volunteer';
import { CoreVolunteerService } from 'app/main/content/_service/core-volunteer.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { Participant } from 'app/main/content/_model/participant';

@Component({
  selector: 'app-class-instance-form-editor',
  templateUrl: './class-instance-form-editor.component.html',
  styleUrls: ['./class-instance-form-editor.component.scss'],
  providers: [QuestionService, QuestionControlService]
})
export class ClassInstanceFormEditorComponent implements OnInit {

  marketplace: Marketplace;
  helpseeker: Participant;

  formConfigurations: FormConfiguration[];
  currentFormConfiguration: FormConfiguration;

  returnedClassInstances: ClassInstance[];

  volunteers: Volunteer[];
  selectedVolunteers: Volunteer[];

  canContinue: boolean;
  canFinish: boolean;
  isLoaded = false;


  constructor(private router: Router,
    private route: ActivatedRoute,
    private marketplaceService: CoreMarketplaceService,
    private classDefinitionService: ClassDefinitionService,
    private classInstanceService: ClassInstanceService,
    private questionService: QuestionService,
    private questionControlService: QuestionControlService,
    private volunteerService: CoreVolunteerService,
    private loginService: LoginService
  ) {
    console.log('extras');
    console.log(this.router.getCurrentNavigation().extras.state);
  }

  ngOnInit() {
    let marketplaceId: string;
    const childClassIds: string[] = [];

    this.returnedClassInstances = [];
    this.selectedVolunteers = [];


    Promise.all([
      this.route.params.subscribe(params => {
        marketplaceId = params['marketplaceId'];
      }),
      this.route.queryParams.subscribe(queryParams => {
        let i = 0;
        while (!isNullOrUndefined(queryParams[i])) {
          childClassIds.push(queryParams[i]);
          i++;
        }
      })
    ]).then(() => {
      this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;

        Promise.all([
          this.classDefinitionService.getAllParentsIdMap(this.marketplace, childClassIds).toPromise().then((formConfigurations: FormConfiguration[]) => {

            this.formConfigurations = formConfigurations;

            for (const config of this.formConfigurations) {
              config.formEntry.questions = this.questionService.getQuestionsFromProperties(config.formEntry.classProperties);
              config.formEntry.formGroup = this.questionControlService.toFormGroup(config.formEntry.questions);
            }
          }),

          this.volunteerService.findAll().toPromise().then((volunteers: Volunteer[]) => {
            this.volunteers = volunteers;
          }),

          this.loginService.getLoggedIn().toPromise().then((helpseeker: Participant) => {
            this.helpseeker = helpseeker;
          })

        ]).then(() => {
          this.currentFormConfiguration = this.formConfigurations.pop();
          this.isLoaded = true;
        });


      });
    });
  }

  handleResultEvent(event: FormEntryReturnEventData) {
    const classInstances: ClassInstance[] = [];
    this.currentFormConfiguration.formEntry.formGroup.disable();
    const propertyInstances: PropertyInstance<any>[] = [];

    for (const classProperty of this.currentFormConfiguration.formEntry.classProperties) {
      const values = [event.formGroup.value[classProperty.id]];
      propertyInstances.push(new PropertyInstance(classProperty, values));
    }

    for (const enumRepresentation of this.currentFormConfiguration.formEntry.enumRepresentations) {
      const values = [event.formGroup.value[enumRepresentation.classDefinition.id]];
      const propertyInstance = new PropertyInstance(enumRepresentation.classDefinition.properties[0], values);
      propertyInstance.name = enumRepresentation.classDefinition.name;
      propertyInstance.id = enumRepresentation.id;
      propertyInstances.push(propertyInstance);
    }

    for (const selectedVolunteer of this.selectedVolunteers) {
      const classInstance: ClassInstance = new ClassInstance(this.currentFormConfiguration.formEntry.classDefinitions[0], propertyInstances);
      classInstance.userId = selectedVolunteer.id;
      classInstance.issuerId = this.helpseeker.id;
      classInstances.push(classInstance);
    }
    
    this.classInstanceService.createNewClassInstances(this.marketplace, classInstances).toPromise().then((ret: ClassInstance[]) => {
      // handle returned value if necessary
      if (!isNullOrUndefined(ret)) {
        this.returnedClassInstances.push(...ret);
        this.handleNextClick();
      }
    });
  }

  getDisplayedName(volunteer: Volunteer): string {
    let result = '';

    if (!isNullOrUndefined(volunteer.lastname)) {
      if (!isNullOrUndefined(volunteer.nickname)) {
        result = result + volunteer.nickname;
      } else if (!isNullOrUndefined(volunteer.firstname)) {
        result = result + volunteer.firstname;
      }
      if (!isNullOrUndefined(volunteer.middlename)) {
        result = result + ' ' + volunteer.middlename;
      }
      result = result + ' ' + volunteer.lastname;
    } else {
      result = result + volunteer.username;
    }

    return result;
  }

  getDisplayedNameFromUserId(userId: string) {
    const volunteer = this.volunteers.find(v => v.id === userId);

    if (isNullOrUndefined(volunteer)) {
      return 'a volunteer';
    } else {
      return this.getDisplayedName(volunteer);
    }
  }

  addToSelection(event: any) {
    const volunteer = this.selectedVolunteers.find((v: Volunteer) => {
      return v.id === event.option.value.id;
    });

    if (!isNullOrUndefined(volunteer)) {
      this.selectedVolunteers = this.selectedVolunteers.filter((v: Volunteer) => {
        return v.id !== volunteer.id;
      });
    } else {
      this.selectedVolunteers.push(event.option.value);
    }
  }

  handleNextClick() {
    this.canContinue = false;
    if (this.formConfigurations.length > 0) {
      this.currentFormConfiguration = this.formConfigurations.pop();
    } else {
      this.canFinish = true;
    }
  }

  handleFinishClick() {
    this.router.navigate(['/main/helpseeker/asset-inbox']);
  }

  handleCancelEvent() {
    this.navigateBack();
  }

  printAnything(anything: any) {
    console.log(anything);
  }


  navigateBack() {
    window.history.back();
  }

}
