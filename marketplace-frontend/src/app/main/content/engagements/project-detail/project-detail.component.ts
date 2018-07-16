import {Component, OnInit} from '@angular/core';
import {Marketplace} from '../../_model/marketplace';
import {ActivatedRoute} from '@angular/router';
import {Project} from '../../_model/project';
import {ProjectService} from '../../_service/project.service';
import {CoreMarketplaceService} from '../../_service/core-marketplace.service';

@Component({
  templateUrl: './project-detail.component.html',
  styleUrls: ['./project-detail.component.scss']
})
export class FuseProjectDetailComponent implements OnInit {
  project: Project;

  constructor(private route: ActivatedRoute,
              private projectService: ProjectService,
              private marketplaceService: CoreMarketplaceService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadTask(params['marketplaceId'], params['projectId']));
  }


  private loadTask(marketplaceId: string, projectId: string) {
    this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.projectService.findById(marketplace, projectId).toPromise().then((project: Project) => this.project = project);
    });
  }
}

