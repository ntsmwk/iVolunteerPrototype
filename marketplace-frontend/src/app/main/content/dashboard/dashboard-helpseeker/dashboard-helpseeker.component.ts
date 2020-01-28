import { OnInit, Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { fuseAnimations } from '@fuse/animations';
import { isNullOrUndefined } from 'util';
import { Router } from '@angular/router';
import { Participant } from '../../_model/participant';
import { LoginService } from '../../_service/login.service';


@Component({
  selector: 'dashboard-helpseeker',
  templateUrl: './dashboard-helpseeker.component.html',
  styleUrls: ['dashboard-helpseeker.scss'],
  animations: fuseAnimations
})
export class DashboardHelpSeekerComponent implements OnInit {

  participant: Participant;

  constructor(private loginService: LoginService,
    private router: Router) {
  }

  ngOnInit() { 

    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.participant = participant;
    });

  }


  private isFF() {
    return this.participant.username== 'FFA';
  }

  private isMV(){
    return this.participant.username==='MVS';
  }
  private isOther(){
    return !this.isFF()&& !this.isMV();
  }
}