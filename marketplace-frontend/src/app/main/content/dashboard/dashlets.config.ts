import {isNullOrUndefined} from 'util';

import {FuseProjectMembersComponent} from '../_components/project-members/project-members.component';
import {FuseTimelineComponent} from '../_components/timeline/timeline.component';
import {FuseTimelineActivitiesComponent} from '../_components/timeline-activities/timeline-activities.component';
import {FuseTimelineTasksComponent} from '../_components/timeline-tasks/timeline-tasks.component';

export class DashletsConf {

  private static dashletEntries: DashletEntry[] = [
    {
      id: 'project-members',
      name: 'Project Members',
      type: FuseProjectMembersComponent,
      cols: 12,
      rows: 16
    }, {
      id: 'timeline',
      name: 'Timeline',
      type: FuseTimelineComponent,
      cols: 32,
      rows: 32 * 1.5,
    }, {
      id: 'timeline-activities',
      name: 'Timeline Activities',
      type: FuseTimelineActivitiesComponent,
      cols: 12,
      rows: 16
    },
    {
      id: 'timeline-tasks',
      name: 'Timeline Tasks',
      type: FuseTimelineTasksComponent,
      cols: 12,
      rows: 24
    }
  ];

  public static getDashletEntries(): DashletEntry[] {
    return this.dashletEntries;
  }

  public static getDashletEntryById(id: string): DashletEntry {
    const dashletEntry = this.dashletEntries.find((entry) => entry.id === id);

    if (!isNullOrUndefined(dashletEntry)) {
      return dashletEntry;
    } else {
      console.error('Dashlet entry with id "' + id + '" is undefined!');
    }
  }
}

export class DashletEntry {
  id: string;
  name: string;
  type: any;
  cols: number;
  rows: number;
}
