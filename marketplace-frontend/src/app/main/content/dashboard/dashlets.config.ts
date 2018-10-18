import {isNullOrUndefined} from 'util';
import {FuseProjectMembersComponent} from '../_components/project-members/project-members.component';

export class DashletsConf {

  private static dashletEntries: DashletEntry[] = [
    {
      id: 'project-members',
      name: 'Project Members',
      type: FuseProjectMembersComponent,
      initItemCols: 24,
      initItemRows: 16
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
  initItemRows: number;
  initItemCols: number;
}
