import { OnInit, Component } from '@angular/core';


@Component({
  selector: 'browse-sub-dialog',
  templateUrl: './browse-sub-dialog.component.html',
  styleUrls: ['./browse-sub-dialog.component.scss']
})
export class BrowseSubDialogComponent implements OnInit {


  constructor(

  ) {

  }

  ngOnInit() {
  }


  navigateBack() {
    window.history.back();
  }

}
