import {Component, OnInit} from '@angular/core';
import {fuseAnimations} from '../../../../../@fuse/animations';

@Component({
  selector: 'fuse-preferences',
  templateUrl: './preferences.component.html',
  styleUrls: ['./preferences.component.scss'],
  animations: fuseAnimations
})

export class PreferencesComponent implements OnInit {
  soloGroup: string;
  soloGroupOptions: string[] = ['Solo Task', 'Group Task'];

  physicalVirtual: string;
  physicalVirtualOptions: string[] = ['Physical Task', 'Virtual Task'];

  weekdays: string[] = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];

  constructor() {
  }

  ngOnInit() {
  }

}
