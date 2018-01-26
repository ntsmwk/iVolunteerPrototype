import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {ElectronService} from '../../providers/electron.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(private translate: TranslateService,
              public electronService: ElectronService) {

    translate.setDefaultLang('en');

    if (electronService.isElectron()) {
      console.log('Mode electron');
      console.log('c', electronService.ipcRenderer);
      console.log('c', electronService.childProcess);
    } else {
      console.log('Mode web');
    }
  }
}
