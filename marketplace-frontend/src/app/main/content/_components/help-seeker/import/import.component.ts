import { Component, OnInit } from '@angular/core';
import { LoginService } from 'app/main/content/_service/login.service';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import {
  ClassDefinition,
  ClassInstance,
} from 'app/main/content/_model/meta/class';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { Marketplace } from 'app/main/content/_model/marketplace';
import {
  ParticipantRole,
  Participant,
} from 'app/main/content/_model/participant';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';
import { CoreVolunteerService } from 'app/main/content/_service/core-volunteer.service';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';

@Component({
  selector: "import",
  templateUrl: 'import.component.html',
  styleUrls: ['import.component.scss'],
})
export class ImportComponent implements OnInit {
  classDefinitions: ClassDefinition[] = [];
  volunteers: Participant[] = [];
  helpseeker: Helpseeker;
  marketplace: Marketplace;
  role: ParticipantRole;
  importForm: FormGroup;

  constructor(
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private helpSeekerService: CoreHelpSeekerService,
    private volunteerService: CoreVolunteerService,
    private classInstanceService: ClassInstanceService,
    private classDefinitionService: ClassDefinitionService
  ) {
    this.importForm = formBuilder.group({
      classDefinition: new FormControl(undefined),
      volunteer: new FormControl(undefined),
      file: new FormControl(undefined),
      fileBtn: new FormControl(undefined),
    });
  }

  async ngOnInit() {
    this.helpseeker = <Helpseeker>(
      await this.loginService.getLoggedIn().toPromise()
    );

    this.marketplace = <Marketplace>(
      await this.helpSeekerService
        .findRegisteredMarketplaces(this.helpseeker.id)
        .toPromise()
    );

    this.classDefinitions = <ClassDefinition[]>(
      await this.classDefinitionService
        .getAllClassDefinitionsWithoutRootAndEnums(
          this.marketplace,
          this.helpseeker.tenantId
        )
        .toPromise()
    );

    this.volunteers = <Participant[]>(
      await this.volunteerService
        .findAllByTenantId(this.helpseeker.tenantId)
        .toPromise()
    );
  }

  async save() {
    // TODO MWE check all form are inputted.. ;)

    const fileReader = new FileReader();
    fileReader.onload = async (e) => {
      const contentObject = JSON.parse(<string>fileReader.result);
      console.error(contentObject);

      for (const entry of contentObject.properties) {
        console.error(entry);
        await this.classInstanceService
          .createClassInstanceByClassDefinitionId(
            this.marketplace,
            contentObject.classDefinitionId,
            this.importForm.value.volunteer.id,
            contentObject.tenantId,
            entry
          )
          .toPromise();
      }
    };
    fileReader.readAsText(this.importForm.value.file.files[0]);
  }
}
