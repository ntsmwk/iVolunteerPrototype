import { Component, OnInit, ViewChild, Inject } from '@angular/core';

import { FuseTranslationLoaderService } from '@fuse/services/translation-loader.service';

import { Chart } from "chart.js";
import { MatTableDataSource, MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material';


const DATA = [
  { engAsset: 'Project Management', assetType: 'Competence', issuer: 'ÖRK', date: '-', status: 'close' },
  { engAsset: 'Yearly Feedback', assetType: 'Feedback', issuer: 'ÖRK', date: '3.12.2018', status: 'check' },
  { engAsset: 'Diligence', assetType: 'Competence', issuer: 'ÖRK', date: '1.7.2018', status: 'check' },
  { engAsset: 'Communication Skills', assetType: 'Competence', issuer: 'ÖRK', date: '1.1.2018', status: 'check' },
  { engAsset: 'Teamwork', assetType: 'Competence', issuer: 'ÖRK', date: '1.1.2018', status: 'check' },
];

// const DATA_TASKS = [
//   {label: 'Task 1', issuer: 'ÖRK', date: new Date(1559033133000), status: 'Bereit zum Veröffentlichen'},
//   {label: 'Task 2', issuer: 'FF Krems', date: new Date(1558820054000), status: 'Veröffentlicht'},
//   {label: 'Task 2', issuer: 'FF Krems', date: new Date(1558788901000), status: 'Veröffentlicht'},
//   {label: 'Task 2', issuer: 'ÖRK', date: new Date(1558513352000), status: 'Abgelaufen'},
//   {label: 'Task 2', issuer: 'ÖRK', date: new Date(1558314179000), status: 'Beendet'}


// ];

const DATA_TASKS = [
  { label: 'Medical Care Transport', issuer: 'ÖRK', date1: '10.06.2019', date2: '2:00 hrs', status: 'none' },
  { label: 'Donation Collection', issuer: 'ÖRK', date1: '05.06.2019', date2: '1:30 hrs', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '30.05.2019', date2: '30 mins', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '28.05.2019', date2: '35 mins', status: 'none' },
  { label: 'Equipment Service', issuer: 'FF Altenberg', date1: '15.05.2019', date2: '1:20 hrs', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '14.05.2019', date2: '25 mins', status: 'none' },

]

const DATA_TASKS_CHANGED = [
  { label: 'Check Tire Pressure', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '10 mins', status: 'new' },
  { label: 'Medical Care Transport', issuer: 'ÖRK', date1: '19.06.2019', date2: '1:30 hrs', status: 'new' },
  { label: 'Medical Care Transport', issuer: 'ÖRK', date1: '10.06.2019', date2: '2:00 hrs', status: 'none' },
  { label: 'Donation Collection', issuer: 'ÖRK', date1: '05.06.2019', date2: '1:30 hrs', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '30.05.2019', date2: '30 mins', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '28.05.2019', date2: '35 mins', status: 'none' },
  { label: 'Equipment Service', issuer: 'FF Altenberg', date1: '15.05.2019', date2: '1:20 hrs', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '14.05.2019', date2: '25 mins', status: 'none' },
]

const DATA_TASKS_CHANGED_2 = [
  { label: 'Check Tire Pressure', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '10 mins', status: 'none' },
  { label: 'Medical Care Transport', issuer: 'ÖRK', date1: '19.06.2019', date2: '1:30 hrs', status: 'none' },
  { label: 'Medical Care Transport', issuer: 'ÖRK', date1: '10.06.2019', date2: '2:00 hrs', status: 'none' },
  { label: 'Donation Collection', issuer: 'ÖRK', date1: '05.06.2019', date2: '1:30 hrs', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '30.05.2019', date2: '30 mins', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '28.05.2019', date2: '35 mins', status: 'none' },
  { label: 'Equipment Service', issuer: 'FF Altenberg', date1: '15.05.2019', date2: '1:20 hrs', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '14.05.2019', date2: '25 mins', status: 'none' },
]

const DATA_TASKS_CHANGED_3 = [
  { label: 'Lead Firebrigade Operation', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '3:15 hrs', status: 'new' },
  { label: 'Check Tire Pressure', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '10 mins', status: 'none' },
  { label: 'Medical Care Transport', issuer: 'ÖRK', date1: '19.06.2019', date2: '1:30 hrs', status: 'none' },
  { label: 'Medical Care Transport', issuer: 'ÖRK', date1: '10.06.2019', date2: '2:00 hrs', status: 'none' },
  { label: 'Donation Collection', issuer: 'ÖRK', date1: '05.06.2019', date2: '1:30 hrs', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '30.05.2019', date2: '30 mins', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '28.05.2019', date2: '35 mins', status: 'none' },
  { label: 'Equipment Service', issuer: 'FF Altenberg', date1: '15.05.2019', date2: '1:20 hrs', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '14.05.2019', date2: '25 mins', status: 'none' },
]

const DATA_TASKS_CHANGED_4 = [
  { label: 'Lead Firebrigade Operation', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '3:15 hrs', status: 'none' },
  { label: 'Check Tire Pressure', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '10 mins', status: 'none' },
  { label: 'Medical Care Transport', issuer: 'ÖRK', date1: '19.06.2019', date2: '1:30 hrs', status: 'none' },
  { label: 'Medical Care Transport', issuer: 'ÖRK', date1: '10.06.2019', date2: '2:00 hrs', status: 'none' },
  { label: 'Donation Collection', issuer: 'ÖRK', date1: '05.06.2019', date2: '1:30 hrs', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '30.05.2019', date2: '30 mins', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '28.05.2019', date2: '35 mins', status: 'none' },
  { label: 'Equipment Service', issuer: 'FF Altenberg', date1: '15.05.2019', date2: '1:20 hrs', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '14.05.2019', date2: '25 mins', status: 'none' },
]





const COMP_DATA = [
  // {label: 'Leadership Skills', issuer: 'FF Altenberg', date1: '14.06.2019', date2: '14.06.2019', status: 'new'},
  { label: 'Firetruck Driver', issuer: 'FF Altenberg', date1: '14.05.2019', date2: '14.05.2019', status: 'none' },
  { label: 'Project Management', issuer: 'ÖRK', date1: '12.04.2019', date2: '12.04.2019', status: 'none' },
  { label: 'Communication Skills', issuer: 'FF Altenberg', date1: '15.02.2017', date2: '15.02.2017', status: 'none' },
  { label: 'Teamwork', issuer: 'ÖRK', date1: '10.01.2018', date2: '10.01.2018', status: 'none' },
  { label: 'Diligence', issuer: 'ÖRK', date1: '10.01.2018', date2: '10.01.2018', status: 'none' },

];

const COMP_DATA_CHANGED = [
  { label: 'Leadership Skills', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '14.06.2019', status: 'new' },
  { label: 'Firetruck Driver', issuer: 'FF Altenberg', date1: '15.02.2017', date2: '14.05.2019', status: 'none' },
  { label: 'Project Management', issuer: 'ÖRK', date1: '12.04.2019', date2: '12.04.2019', status: 'none' },
  { label: 'Communication Skills', issuer: 'FF Altenberg', date1: '15.02.2017', date2: '15.02.2017', status: 'none' },
  { label: 'Teamwork', issuer: 'ÖRK', date1: '10.01.2018', date2: '10.01.2018', status: 'none' },
  { label: 'Diligence', issuer: 'ÖRK', date1: '10.01.2018', date2: '10.01.2018', status: 'none' },

];

const COMP_DATA_CHANGED_2 = [
  { label: 'Leadership Skills', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '14.06.2019', status: 'none' },
  { label: 'Firetruck Driver', issuer: 'FF Altenberg', date1: '15.02.2017', date2: '14.05.2019', status: 'none' },
  { label: 'Project Management', issuer: 'ÖRK', date1: '12.04.2019', date2: '12.04.2019', status: 'none' },
  { label: 'Communication Skills', issuer: 'FF Altenberg', date1: '15.02.2017', date2: '15.02.2017', status: 'none' },
  { label: 'Teamwork', issuer: 'ÖRK', date1: '10.01.2018', date2: '10.01.2018', status: 'none' },
  { label: 'Diligence', issuer: 'ÖRK', date1: '10.01.2018', date2: '10.01.2018', status: 'none' },

];

const FEEDBACK_DATA = [
  { label: 'Firetruck Driver Renewed:', issuer: 'FF Altenberg', date1: '12.04.2019', date2: '-', status: 'none', type: 'kudos' },
  { label: 'Yearly Feedback:', issuer: 'ÖRK', date1: '31.12.2018', date2: '-', status: 'none', type: 'star' },



];

const FEEDBACK_DATA_CHANGED = [
  { label: 'Check Tire Pressure', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '-', status: 'new', type: 'kudos' },
  { label: 'Medical Care Transport:', issuer: 'ÖRK', date1: '19.06.2019', date2: '-', status: 'new', type: 'star' },
  { label: 'Firetruck Driver Renewed:', issuer: 'FF Altenberg', date1: '12.04.2019', date2: '-', status: 'none', type: 'kudos' },
  { label: 'Yearly Feedback:', issuer: 'ÖRK', date1: '31.12.2018', date2: '-', status: 'none', type: 'star' },


];

const FEEDBACK_DATA_CHANGED_2 = [
  { label: 'Check Tire Pressure', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '-', status: 'none', type: 'kudos' },
  { label: 'Medical Care Transport:', issuer: 'ÖRK', date1: '19.06.2019', date2: '-', status: 'none', type: 'star' },
  { label: 'Firetruck Driver Renewed:', issuer: 'FF Altenberg', date1: '12.04.2019', date2: '-', status: 'none', type: 'kudos' },
  { label: 'Yearly Feedback:', issuer: 'ÖRK', date1: '31.12.2018', date2: '-', status: 'none', type: 'star' },


];


const FEEDBACK_DATA_CHANGED_3 = [
  { label: 'Lead Firebrigade Operation:', issuer: 'FF Altenberg', date1: '14.06.2019', date2: '-', status: 'new', type: 'star' },
  { label: 'Check Tire Pressure', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '-', status: 'none', type: 'kudos' },
  { label: 'Medical Care Transport:', issuer: 'ÖRK', date1: '19.06.2019', date2: '-', status: 'none', type: 'star' },
  { label: 'Firetruck Driver Renewed:', issuer: 'FF Altenberg', date1: '12.04.2019', date2: '-', status: 'none', type: 'kudos' },
  { label: 'Yearly Feedback:', issuer: 'ÖRK', date1: '31.12.2018', date2: '-', status: 'none', type: 'star' },


];

const FEEDBACK_DATA_CHANGED_4 = [
  { label: 'Lead Firebrigade Operation:', issuer: 'FF Altenberg', date1: '14.06.2019', date2: '-', status: 'none', type: 'star' },
  { label: 'Check Tire Pressure', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '-', status: 'none', type: 'kudos' },
  { label: 'Medical Care Transport:', issuer: 'ÖRK', date1: '19.06.2019', date2: '-', status: 'none', type: 'star' },
  { label: 'Firetruck Driver Renewed:', issuer: 'FF Altenberg', date1: '12.04.2019', date2: '-', status: 'none', type: 'kudos' },
  { label: 'Yearly Feedback:', issuer: 'ÖRK', date1: '31.12.2018', date2: '-', status: 'none', type: 'star' },


];


@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  host: {
    '(document:keypress)': 'handleKeyboardEvent($event)'
  }
})
export class DashboardComponent implements OnInit {

  @ViewChild('myChart1') private chartRef1;
  chart1: any;

  @ViewChild('myChart2') private chartRef2;
  chart2: any;

  @ViewChild('myChart3') private chartRef3;
  chart3: any;
  chart3meta: any;

  widget0: any;
  widget1: any;
  widget2: any;
  widget3: any;


  dataSource = new MatTableDataSource<any>();
  displayedColumns = ['engAsset', 'assetType', 'issuer', 'date', 'status'];

  // dataSourceTasks = new MatTableDataSource<any>();
  // displayedColumnsTasks = ['label', 'issuer', 'date', 'status', 'actions'];

  dataSourceComp = new MatTableDataSource<any>();
  dataSourceFeedback = new MatTableDataSource<any>();
  dataSourceTasks = new MatTableDataSource<any>();
  displayedColumnsCompetence = ['label_competence', 'issuer', 'date1'/*, 'evidence', 'date2'*/, 'details', 'status'];
  displayedColumnsFeedback = ['label_feedback', 'issuer', 'date1'/*, 'evidence', 'date2'*/, 'details', 'status'];
  displayedColumnsTasks = ['label_tasks', 'issuer', 'date1'/*, 'evidence'*/, 'date2', 'details', 'status'];

  highlightedRow;

  leadershipGained: boolean;

  state1: boolean;
  state2: boolean;
  state3: boolean;
  state4: boolean;
  state5: boolean;
  state6: boolean;
  state7: boolean;

  constructor(public dialog: MatDialog) {

  }

  ngOnInit() {


    this.dataSource.data = DATA;
    this.dataSourceTasks.data = DATA_TASKS;
    this.dataSourceFeedback.data = FEEDBACK_DATA;
    this.dataSourceComp.data = COMP_DATA;

    this.leadershipGained = false;
    this.state1 = true;
    this.state2 = false;
    this.state3 = false;
    this.state4 = false;
    this.state5 = false;
    this.state6 = false;
    this.state7 = false;


    this.chart1 = new Chart(this.chartRef1.nativeElement, {
      type: 'pie',
      data: {
        datasets: [{
          label: '# of Votes',
          data: [12, 19, 3, 5, 2, 3],
          backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(255, 159, 64, 0.2)'
          ],
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(255, 159, 64, 1)'
          ],
          borderWidth: 1
        }]
      },
      options: {
        responsive: false,
      }
    });

    this.chart2 = new Chart(this.chartRef2.nativeElement, {

      type: 'doughnut',
      data: {
        datasets: [{
          label: '# of Votes',
          data: [12, 19, 3, 5, 2, 3],
          backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(255, 159, 64, 0.2)'
          ],
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(255, 159, 64, 1)'
          ],
          borderWidth: 1
        }]
      },
      options: {
        responsive: false,
      }
    });

    this.chart3 = new Chart(this.chartRef3.nativeElement, {
      type: 'bar',
      data: {
        datasets: [{
          label: 'Visits',
          data: [432, 428, 327, 363, 456, 267, 231],
          borderColor: '#f44336',
          backgroundColor: '#f44336'
        }],
        labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
      },
      options: {
        responsive: false,
        spanGaps: false,
        legend: {
          display: false
        },
        maintainAspectRatio: false,
        layout: {
          padding: {
            top: 24,
            left: 16,
            right: 16,
            bottom: 16
          }
        },
        scales: {
          xAxes: [
            {
              display: false
            }
          ],
          yAxes: [
            {
              display: false,
              ticks: {
                min: 150,
                max: 500
              }
            }
          ]
        }
      }
    });

    this.chart3meta = {
      visits: {
        value: 882,
        ofTarget: -9
      },
    };


    this.widget0 = {
      'title': 'Tasks finished (this month)',
      'data': {
        'label': 'TASKS',
        'count': 50,
        'extra': {
          'label': 'Yesterday',
          'count': 47
        }
      },
      'detail': 'You can show some detailed information about this widget in here.'
    };

    this.widget1 = {
      'title': 'Tasks still open',
      'data': {
        'label': 'TASKS',
        'count': 22,
        'extra': {
          'label': 'Yesterday',
          'count': 25
        }
      },
      'detail': 'You can show some detailed information about this widget in here.'
    };

    this.widget2 = {
      'title': 'Your open Tasks at ÖRK',
      'data': {
        'label': 'TASKS',
        'count': 12,
        'extra': {
          'label': 'Yesterday',
          'count': 14
        }
      },
      'detail': 'You can show some detailed information about this widget in here.'
    };

    this.widget3 = {
      'title': 'Your open Tasks at FF Krems',
      'data': {
        'label': 'TASKS',
        'count': 10,
        'extra': {
          'label': 'Yesterday',
          'count': 11
        }
      },
      'detail': 'You can show some detailed information about this widget in here.'
    };

    console.log(this.chart3);
    console.log(this.widget1);
    console.log("Dashboard");
  }



  



  toggleState4() {
    this.dataSourceTasks.data = DATA_TASKS_CHANGED;
    this.dataSourceComp.data = COMP_DATA;
    this.dataSourceFeedback.data = FEEDBACK_DATA_CHANGED;
    this.leadershipGained = false;
    this.state1 = false;
    this.state2 = false;
    this.state3 = false;
    this.state4 = true;
    this.state5 = false;
    this.state6 = false;
    this.state7 = false;

  }

  toggleState6() {
    this.dataSourceTasks.data = DATA_TASKS_CHANGED_3;
    this.dataSourceComp.data = COMP_DATA_CHANGED;
    this.dataSourceFeedback.data = FEEDBACK_DATA_CHANGED_3;
    this.leadershipGained = true;
    this.state1 = false;
    this.state2 = false;
    this.state3 = false;
    this.state4 = false;
    this.state5 = false;
    this.state6 = true;
    this.state7 = false;

  }

  toggleState7() {
    this.dataSourceTasks.data = DATA_TASKS_CHANGED_4;
    this.dataSourceComp.data = COMP_DATA_CHANGED_2;
    this.dataSourceFeedback.data = FEEDBACK_DATA_CHANGED_4;
    this.leadershipGained = true;
    this.state1 = false;
    this.state2 = false;
    this.state3 = false;
    this.state4 = false;
    this.state5 = false;
    this.state6 = false;
    this.state7 = true;
  }

  handleKeyboardEvent(event: KeyboardEvent) {


    if (event.key == '1') {
      this.dataSourceComp.data = COMP_DATA;
      this.dataSourceTasks.data = DATA_TASKS;
      this.dataSourceFeedback.data = FEEDBACK_DATA;
      this.leadershipGained = false;
      this.state1 = true;
      this.state2 = false;
      this.state3 = false;
      this.state4 = false;
      this.state5 = false;
      this.state6 = false;
      this.state7 = false;



    }

    if (event.key == '2') {
      this.dataSourceComp.data = COMP_DATA;
      this.dataSourceTasks.data = DATA_TASKS;
      this.dataSourceFeedback.data = FEEDBACK_DATA;
      this.leadershipGained = false;
      this.state1 = false;
      this.state2 = true;
      this.state3 = false;
      this.state4 = false;
      this.state5 = false;
      this.state6 = false;
      this.state7 = false;

    }

    if (event.key == '3') {
      this.dataSourceComp.data = COMP_DATA;
      this.dataSourceTasks.data = DATA_TASKS;
      this.dataSourceFeedback.data = FEEDBACK_DATA;
      this.leadershipGained = false;
      this.state1 = false;
      this.state2 = false;
      this.state3 = true;
      this.state4 = false;
      this.state5 = false;
      this.state6 = false;
      this.state7 = false;

    }

    if (event.key == '4') {

      this.toggleState4();

    }

    if (event.key == '5') {
      this.dataSourceTasks.data = DATA_TASKS_CHANGED_2;
      this.dataSourceComp.data = COMP_DATA;
      this.dataSourceFeedback.data = FEEDBACK_DATA_CHANGED_2;
      this.leadershipGained = true;
      this.state1 = false;
      this.state2 = false;
      this.state3 = false;
      this.state4 = false;
      this.state5 = true;
      this.state6 = false;
      this.state7 = false;
    }

    if (event.key == '6') {
      this.toggleState6();

    }

    if (event.key == '7') {
      this.toggleState7();
    }


  }

  triggerShareDialog() {
    const dialogRef = this.dialog.open(ShareDialog, {
      width: '700px',
      height: '255px',
      data: { name: "share" }
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      console.log('The dialog was closed');
      this.toggleState7();
      console.log("done")
    });
  }

  triggerStoreDialog() {

    const dialogRef = this.dialog.open(ShareDialog, {
      width: '700px',
      height: '255px',
      data: { name: "store" }
    });
  

    dialogRef.afterClosed().subscribe((result: any) => {
      console.log('The dialog was closed');
      this.toggleShareInRep();
      console.log("done")
    });
  }


  toggleShareInRep() {
    if (this.state3) {
      this.toggleState4();
    } else if (this.state5) {
      this.toggleState6();
    } else if (this.state6) {
    }
  }



}

export interface DialogData {
  name: string;
}

@Component({
  selector: 'share-dialog',
  templateUrl: 'share-dialog.html',
  styleUrls: ['./dashboard.component.scss']
})
export class ShareDialog {

  shareDone: boolean;
  duringShare: boolean;
  percentageValue: number;

  constructor(
    public dialogRef: MatDialogRef<ShareDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {
    this.shareDone = false;
    this.duringShare = false;
    this.percentageValue = 0;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  triggeredShare() {
    this.duringShare= true;
    setTimeout( () => {
          this.duringShare = false;
          this.shareDone = true;
    
        }, 2000)
  }

  // onClickedUpload(): void {
  //   this.duringUpload = true;


  //   this.advanceTime();



  //   console.log("clicked upload");

  // }

  // advanceTime(): void {
  //   setTimeout( () => {
  //     if (this.percentageValue == 100) {
  //       this.duringUpload = false;
  //       this.uploadFinished = true;
  //       this.dialogRef.close(true);
  //     } else {
  //       //bla
  //       this.percentageValue+=1;
  //       this.advanceTime();
  //     }

  //   }, 20)
  // }



}
