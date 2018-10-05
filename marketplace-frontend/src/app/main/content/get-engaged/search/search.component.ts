import { Component, OnInit } from '@angular/core';
import {fuseAnimations} from '../../../../../@fuse/animations';

@Component({
  selector: 'fuse-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
  animations: fuseAnimations

})
export class SearchComponent implements OnInit {
  radioOptions = 'test1';


  constructor() { }

  ngOnInit() {
  }

}
