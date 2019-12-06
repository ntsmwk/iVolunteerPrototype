import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';

import { Participant } from '../_model/participant';
import { Marketplace } from '../_model/marketplace';

import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { LoginService } from '../_service/login.service';
import { TaskService } from '../_service/task.service';
import { fuseAnimations } from '@fuse/animations';
import { isNullOrUndefined } from 'util';
import { DerivationRule } from '../_model/derivation-rule';


@Component({
    selector: 'fuse-rule-overview',
    templateUrl: './rule-overview.component.html',
    styleUrls: ['./rule-overview.component.scss'],
    animations: fuseAnimations

})
export class FuseRuleOverviewComponent implements OnInit {
    marketplaces: Marketplace[];
    dataSource = new MatTableDataSource<DerivationRule>();
    displayedColumns = ['name', 'sources', 'targets'];

    constructor(private router: Router,
        private loginService: LoginService,
        private helpSeekerService: CoreHelpSeekerService) {
    }

    ngOnInit() {
        this.loadAllDerivationRules();
    }

    onRowSelect(derivationRule: DerivationRule) {
        this.router.navigate(['/main/rule/' + derivationRule.id]);
    }

    private loadAllDerivationRules() {
        this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
            this.helpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
                if (!isNullOrUndefined(marketplace)) {


                    // this.marketplaces = [].concat(marketplace);
                    // this.taskService.findAll(marketplace).toPromise().then((tasks: Task[]) => this.dataSource.data = tasks);
                }
            });
        });
    }

    addDerivationRule() {
        this.router.navigate(['/main/rule']);
    }
}
