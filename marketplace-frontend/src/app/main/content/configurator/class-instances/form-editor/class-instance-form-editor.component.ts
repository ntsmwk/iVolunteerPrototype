import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../_model/marketplace';
import { ClassDefinitionService } from '../../../_service/meta/core/class/class-definition.service';
import { ClassDefinition, ClassInstance, ClassArchetype } from '../../../_model/meta/Class';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { QuestionService } from 'app/main/content/_service/question.service';
import { QuestionBase } from 'app/main/content/_model/dynamic-forms/questions';
import { FormControl, FormBuilder, FormGroup, FormArray } from '@angular/forms';
import { isNullOrUndefined } from 'util';
import { maxOtherNew } from "../../../_validator/custom.validators";
import { DataTransportService } from 'app/main/content/_service/data-transport/data-transport.service';
import { FormConfiguration, FormEntryReturnEventData } from 'app/main/content/_model/meta/form';
import { QuestionControlService } from 'app/main/content/_service/question-control.service';
import { isNull } from '@angular/compiler/src/output/output_ast';
import { ClassProperty, PropertyInstance } from 'app/main/content/_model/meta/Property';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';


@Component({
  selector: 'app-class-instance-form-editor',
  templateUrl: './class-instance-form-editor.component.html',
  styleUrls: ['./class-instance-form-editor.component.scss'],
  providers: [QuestionService, QuestionControlService]
})
export class ClassInstanceFormEditorComponent implements OnInit {

  marketplace: Marketplace;
  formConfigurations: FormConfiguration[];

  isLoaded: boolean = false;


  constructor(private router: Router,
    private route: ActivatedRoute,
    private marketplaceService: CoreMarketplaceService,
    private classDefinitionService: ClassDefinitionService,
    private classInstanceService: ClassInstanceService,
    private questionService: QuestionService,
    private questionControlService: QuestionControlService,
    private dataTransportService: DataTransportService,
  ) {

  }

  ngOnInit() {

    this.route.params.subscribe(param => {
      const marketplaceId = param['marketplaceId'];

      console.log("test datatransportservice:")
      console.log(this.dataTransportService.data)
      
      // let rootClassIds = this.dataTransportService.data;
      let childClassIds = ['test8', 'test7', 'test9'];
      

      this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;
          this.classDefinitionService.getAllParentsIdMap(this.marketplace, childClassIds).toPromise().then((formConfigurations: FormConfiguration[]) => {
           
            this.formConfigurations = formConfigurations
            
            for (let config of this.formConfigurations) {
              for (let entry of config.formEntries) {
                 entry.questions = this.questionService.getQuestionsFromProperties(entry.classProperties);
                 entry.formGroup = this.questionControlService.toFormGroup(entry.questions);  
              }
            }

            this.isLoaded = true;

          }).then(() => {
            
            console.log(this.formConfigurations);
            
  
          });
      });
    });
  }

  handleResultEvent(event: FormEntryReturnEventData) {
    let formConfiguration = this.formConfigurations.find((fc: FormConfiguration) => {
      return fc.id == event.formConfigurationId
    })

    let classInstances: ClassInstance[] = [];
    for(let entry of formConfiguration.formEntries) {
      entry.formGroup.disable();
      let propertyInstances: PropertyInstance<any>[] = [];

      for (let classProperty of entry.classProperties) {
        let values = [event.formGroup.value[classProperty.id]];
        propertyInstances.push(new PropertyInstance(classProperty, values));
      }

      let classInstance: ClassInstance = new ClassInstance(entry.classDefinitions[0], propertyInstances);
      classInstances.push(classInstance);

    }

    
    this.classInstanceService.createNewClassInstances(this.marketplace, classInstances).toPromise().then((ret: ClassInstance[]) => {
      //handle returned value if necessary
    }); 
 
  }

  

  
  printAnything(anything: any) {
    console.log(anything);
  }



  navigateBack() {
    window.history.back();
  }

}
