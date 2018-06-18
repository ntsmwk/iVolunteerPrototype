import {Component, OnInit} from '@angular/core';
import {DateAdapter} from '@angular/material';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {


  constructor(private dateAdapter: DateAdapter<Date>) {
  }

  ngOnInit() {
    this.dateAdapter.setLocale('de');
  }
}
