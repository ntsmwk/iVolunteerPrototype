import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../_model/marketplace';
import { ConfiguratorService } from '../../../_service/configurator.service';
import { ClassDefintion } from '../../../_model/meta/Class';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { QuestionService } from 'app/main/content/_service/question.service';
import { QuestionBase } from 'app/main/content/_model/dynamic-forms/questions';


@Component({
  selector: 'app-class-instance-form-editor',
  templateUrl: './class-instance-form-editor.component.html',
  styleUrls: ['./class-instance-form-editor.component.scss'],
  providers:  [QuestionService]
})
export class ClassInstanceFormEditorComponent implements OnInit {
  
  marketplace: Marketplace;
  configurableClass: ClassDefintion; 

  isLoaded: boolean = false;

  questions: QuestionBase<any>[];

  constructor(private router: Router,
    private route: ActivatedRoute,
    private marketplaceService: CoreMarketplaceService,
    private configuratorService: ConfiguratorService,
    private questionService: QuestionService,
    ){

    }

  ngOnInit() {

    this.route.params.subscribe(param => {
      console.log(param);
      console.log(param['marketplaceId']);

      const marketplaceId = param['marketplaceId'];
      const classId = param['classId'];

      this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;
        this.configuratorService.getConfigClassById(this.marketplace, classId).toPromise().then((configurableClass: ClassDefintion) => {
          this.configurableClass = configurableClass;
          this.questions = this.questionService.getQuestionsFromProperties(this.configurableClass.properties);
          this.isLoaded = true;
          console.log(this.marketplace);
          console.log(this.configurableClass);
        });
      });
    });
  }



  navigateBack() {
    window.history.back();
  }

}
