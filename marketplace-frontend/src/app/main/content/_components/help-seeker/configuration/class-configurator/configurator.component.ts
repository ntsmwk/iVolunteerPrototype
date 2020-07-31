import { Component, OnInit } from '@angular/core';
import { LoginService } from 'app/main/content/_service/login.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';

@Component({
  selector: "app-configurator",
  templateUrl: './configurator.component.html',
  styleUrls: ['./configurator.component.scss']
})
export class ConfiguratorComponent implements OnInit {
  globalInfo: GlobalInfo;
  loaded = false;

  constructor(private loginService: LoginService
  ) { }

  async ngOnInit() {
    this.globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );

    console.log(this.globalInfo);
    this.loaded = true;
  }

  navigateBack() {
    window.history.back();
  }
}
