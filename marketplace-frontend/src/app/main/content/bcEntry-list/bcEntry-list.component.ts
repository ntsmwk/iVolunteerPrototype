import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MatTableDataSource} from '@angular/material';
import {fuseAnimations} from '@fuse/animations';
import {Router} from '@angular/router';
import {CoreBCEntryService} from '../_service/core-bcEntry.service';
import {BCEntry} from '../_model/bcEntry';

@Component({
  templateUrl: './bcEntry-list.component.html',
  styleUrls: ['./bcEntry-list.component.scss'],
  encapsulation: ViewEncapsulation.None,
  animations: fuseAnimations
})
export class FuseBCEntryListComponent implements OnInit {

  dataSource = new MatTableDataSource<BCEntry>();
  displayedColumns = ['name', 'checked', 'delete'];

  constructor(private router: Router,
              private coreBCEntryService: CoreBCEntryService) {
  }

  ngOnInit() {
    this.coreBCEntryService.findAll()
      .toPromise()
      .then((bcEntry: BCEntry[]) => this.dataSource.data = bcEntry);
  }
  
  addBCEntry() {
    this.router.navigate(['/main/bcEntry-form']);
  }

  deleteBCEntry(bcEntry: BCEntry) {
    this.coreBCEntryService.delete(bcEntry)
      .toPromise()
      .then(() => 
        this.coreBCEntryService.findAll()
        .toPromise()
        .then((bcEntry: BCEntry[]) => this.dataSource.data = bcEntry));
  }

  handleChange(bcEntry: BCEntry){
    bcEntry.checked = !bcEntry.checked;
    this.coreBCEntryService.update(bcEntry)
      .toPromise()
      .then(() => this.router.navigate(['/main/bcEntries/all']));
  }
  
}
