import { Component, OnInit } from '@angular/core';
import { LoginService } from 'app/main/content/_service/login.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';

@Component({
  selector: "app-configurator",
  templateUrl: './configurator.component.html',
  styleUrls: ['./configurator.component.scss']
})
export class ConfiguratorComponent implements OnInit {
  globalInfo: GlobalInfo;
  loaded = false;

  constructor(private loginService: LoginService, private classDefService: ClassDefinitionService
  ) { }

  async ngOnInit() {
    this.globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );

    this.loaded = true;
  }

  navigateBack() {
    window.history.back();
  }
}
