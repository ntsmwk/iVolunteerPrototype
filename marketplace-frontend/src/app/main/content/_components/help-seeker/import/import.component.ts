import { Component, OnInit } from "@angular/core";
import { LoginService } from "app/main/content/_service/login.service";
import { FormBuilder, FormControl, FormGroup } from "@angular/forms";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { ClassDefinition } from "app/main/content/_model/meta/Class";
import { Helpseeker } from "app/main/content/_model/helpseeker";
import { Marketplace } from "app/main/content/_model/marketplace";
import { ParticipantRole } from "app/main/content/_model/participant";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";

@Component({
  selector: "import",
  templateUrl: "import.component.html",
  styleUrls: ["import.component.scss"],
})
export class ImportComponent implements OnInit {
  classDefinitions: ClassDefinition[] = [];
  helpseeker: Helpseeker;
  marketplace: Marketplace;
  role: ParticipantRole;
  importForm: FormGroup;

  constructor(
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private helpSeekerService: CoreHelpSeekerService,

    private classDefinitionService: ClassDefinitionService
  ) {
    this.importForm = formBuilder.group({
      classDefinition: new FormControl(undefined),
      file: new FormControl(undefined),
      fileBtn: new FormControl(undefined),
    });
  }

  ngOnInit() {
    this.loginService
      .getLoggedIn()
      .toPromise()
      .then((helpseeker: Helpseeker) => {
        this.helpseeker = helpseeker;

        this.helpSeekerService
          .findRegisteredMarketplaces(helpseeker.id)
          .toPromise()
          .then((marketplace: Marketplace) => {
            this.marketplace = marketplace;

            this.classDefinitionService
              .getAllClassDefinitionsWithoutRootAndEnums(
                marketplace,
                this.helpseeker.tenantId
              )
              .toPromise()
              .then(
                (definitions: ClassDefinition[]) =>
                  (this.classDefinitions = definitions)
              );
          });
      });
  }

  save() {
    // TODO import call...
    //   this.derivationRuleService
    //     .save(this.marketplace, this.derivationRule)
    //     .toPromise()
    //     .then(() =>
    //       this.loadDerivationRule(this.marketplace, this.derivationRule.id)
    //     );
    // }
  }
}
