import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {Project} from '../../../../_model/project';
import {ProjectService} from '../../../../_service/project.service';
import {Marketplace} from '../../../../_model/marketplace';
import {CoreMarketplaceService} from '../../../../_service/core-marketplace.service';

@Component({
  selector: 'fuse-project-member',
  templateUrl: './project-member.component.html',
  styleUrls: ['./project-member.component.scss']
})
export class FuseProjectMemberComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private projectService: ProjectService,
              private marketplaceService: CoreMarketplaceService) {

  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadProjectMembers(params['marketplaceId'], params['projectId']));
  }

  private loadProjectMembers(marketplaceId: string, projectId: string) {
    this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.projectService.findById(marketplace, projectId).toPromise().then((project: Project) => {
      });
    });
  }
}
