import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../_model/marketplace';
import { ClassDefinitionService } from '../../../_service/meta/core/class/class-definition.service';
import { ClassInstance } from '../../../_model/meta/Class';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { QuestionService } from 'app/main/content/_service/question.service';
import { DataTransportService } from 'app/main/content/_service/data-transport/data-transport.service';
import { FormConfiguration, FormEntryReturnEventData } from 'app/main/content/_model/meta/form';
import { QuestionControlService } from 'app/main/content/_service/question-control.service';
import { PropertyInstance } from 'app/main/content/_model/meta/Property';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { isNullOrUndefined } from 'util';
import { config } from 'rxjs';

@Component({
  selector: 'app-class-instance-form-editor',
  templateUrl: './class-instance-form-editor.component.html',
  styleUrls: ['./class-instance-form-editor.component.scss'],
  providers: [QuestionService, QuestionControlService]
})
export class ClassInstanceFormEditorComponent implements OnInit {

  marketplace: Marketplace;
  formConfigurations: FormConfiguration[];
  currentFormConfiguration: FormConfiguration;

  canContinue: boolean;
  canFinish: boolean;

  isLoaded: boolean = false;


  constructor(private router: Router,
    private route: ActivatedRoute,
    private marketplaceService: CoreMarketplaceService,
    private classDefinitionService: ClassDefinitionService,
    private classInstanceService: ClassInstanceService,
    private questionService: QuestionService,
    private questionControlService: QuestionControlService,
  ) {

  }

  ngOnInit() {
    let marketplaceId: string;
    let childClassIds: string[] = [];
    
    Promise.all([
      this.route.params.subscribe(params => {
       marketplaceId = params['marketplaceId'];
      }),
      this.route.queryParams.subscribe(queryParams => {
        let i = 0;
        while(!isNullOrUndefined(queryParams[i])) {
          childClassIds.push(queryParams[i]);
          i++;
        }
      })

    ]).then(() => {
      this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;
        this.classDefinitionService.getAllParentsIdMap(this.marketplace, childClassIds).toPromise().then((formConfigurations: FormConfiguration[]) => {

          this.formConfigurations = formConfigurations

          for (let config of this.formConfigurations) {
              config.formEntry.questions = this.questionService.getQuestionsFromProperties(config.formEntry.classProperties);
              
              config.formEntry.formGroup = this.questionControlService.toFormGroup(config.formEntry.questions);
            
          }


        }).then(() => {
          this.currentFormConfiguration = this.formConfigurations.pop();
          console.log(this.currentFormConfiguration)
          console.log(this.formConfigurations);
          this.isLoaded = true;


        });
      });
    });
  }

  

  handleResultEvent(event: FormEntryReturnEventData) {
    let formConfiguration = this.formConfigurations.find((fc: FormConfiguration) => {
      return fc.id == event.formConfigurationId
    })

    let classInstances: ClassInstance[] = [];
      this.currentFormConfiguration.formEntry.formGroup.disable();
      let propertyInstances: PropertyInstance<any>[] = [];

      for (let classProperty of this.currentFormConfiguration.formEntry.classProperties) {
        let values = [event.formGroup.value[classProperty.id]];
        propertyInstances.push(new PropertyInstance(classProperty, values));
      }

      let classInstance: ClassInstance = new ClassInstance(this.currentFormConfiguration.formEntry.classDefinitions[0], propertyInstances);
      classInstances.push(classInstance);

    


    this.classInstanceService.createNewClassInstances(this.marketplace, classInstances).toPromise().then((ret: ClassInstance[]) => {
      //handle returned value if necessary
      if (!isNullOrUndefined(ret)) {
        this.canContinue = true;
        this.handleNextClick();
      }
    });
  }

  handleNextClick() {
    this.canContinue = false;
    if (this.formConfigurations.length > 0) {
      this.currentFormConfiguration = this.formConfigurations.pop();
    } else {
      this.canFinish = true
    }
  }

  handleFinishClick() {
    this.navigateBack();
  }

  handleCancelEvent() {
    console.log("cancelled");
    this.navigateBack();
  }



  printAnything(anything: any) {
    console.log(anything);
  }



  navigateBack() {
    window.history.back();
  }

}
