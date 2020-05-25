import { Component, Input, OnInit } from "@angular/core";

import { Task } from "../../_model/task";
import { Marketplace } from "../../_model/marketplace";

import { TaskService } from "../../_service/task.service";
import { MarketplaceService } from "../../_service/core-marketplace.service";

@Component({
  selector: "fuse-project-task-list",
  templateUrl: "./project-task-list.component.html",
  styleUrls: ["./project-task-list.component.scss"],
})
export class FuseProjectTaskListComponent implements OnInit {
  @Input("marketplaceId")
  public marketplaceId: string;
  @Input("projectId")
  private projectId: string;
  @Input("participantId")
  public participantId: string;

  @Input("availableOnly")
  public availableOnly: boolean;
  @Input("engagedOnly")
  public engagedOnly: boolean;
  @Input("finishedOnly")
  public finishedOnly: boolean;

  public tasks: Array<Task>;

  constructor(
    private taskService: TaskService,
    private marketplaceService: MarketplaceService
  ) {}

  ngOnInit() {
    this.marketplaceService
      .findById(this.marketplaceId)
      .toPromise()
      .then((marketplace: Marketplace) => {
        if (this.availableOnly === true) {
          this.taskService
            .findAvailableByParticipantAndProject(
              marketplace,
              this.participantId,
              this.projectId
            )
            .toPromise()
            .then((tasks: Array<Task>) => (this.tasks = tasks));
        } else if (this.engagedOnly === true) {
          this.taskService
            .findEngagedByParticipantAndProject(
              marketplace,
              this.participantId,
              this.projectId
            )
            .toPromise()
            .then((tasks: Array<Task>) => (this.tasks = tasks));
        } else if (this.finishedOnly === true) {
          this.taskService
            .findFinishedByParticipantAndProject(
              marketplace,
              this.participantId,
              this.projectId
            )
            .toPromise()
            .then((tasks: Array<Task>) => (this.tasks = tasks));
        } else {
          this.taskService
            .findByProjectId(marketplace, this.projectId)
            .toPromise()
            .then((tasks: Array<Task>) => (this.tasks = tasks));
        }
      });
  }
}
