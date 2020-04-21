import { Component, OnInit } from "@angular/core";
import { LoginService } from "app/main/content/_service/login.service";
import { FormBuilder, FormControl, FormGroup } from "@angular/forms";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import {
  ClassDefinition,
  ClassInstance,
} from "app/main/content/_model/meta/Class";
import { Helpseeker } from "app/main/content/_model/helpseeker";
import { Marketplace } from "app/main/content/_model/marketplace";
import {
  ParticipantRole,
  Participant,
} from "app/main/content/_model/participant";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { CoreVolunteerService } from "app/main/content/_service/core-volunteer.service";
import { PropertyInstance } from "app/main/content/_model/meta/Property";

@Component({
  selector: "import",
  templateUrl: "import.component.html",
  styleUrls: ["import.component.scss"],
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

  save() {
    this.importForm.value.file;

    let fileReader = new FileReader();
    fileReader.onload = (e) => {
      let importContent = fileReader.result;
      this.handleFileContent(importContent);
      // this.importService.import();
    };
    fileReader.readAsText(this.importForm.value.file.files[0]);
    // TODO import call...
    //   this.derivationRuleService
    //     .save(this.marketplace, this.derivationRule)
    //     .toPromise()
    //     .then(() =>
    //       this.loadDerivationRule(this.marketplace, this.derivationRule.id)
    //     );
    // }
  }

  handleFileContent(content) {
    const classInstances: ClassInstance[] = [];
    const propertyInstances: PropertyInstance<any>[] = [];
    let fileContentObject = JSON.parse(content);
    for (const entry of fileContentObject) {
      this.handleEntry(entry);
    }
  }

  handleEntry(entry) {
    for (const classProperty of Object.keys(entry)) {
      console.error(classProperty);
      console.error(entry[classProperty]);

      // const values = [event.formGroup.value[classProperty.id]];
      // propertyInstances.push(new PropertyInstance(classProperty, values));
    }
    // for (const enumRepresentation of this.currentFormConfiguration.formEntry
    //   .enumRepresentations) {
    //   const values = [
    //     event.formGroup.value[enumRepresentation.classDefinition.id]
    //   ];
    //   const propertyInstance = new PropertyInstance(
    //     enumRepresentation.classDefinition.properties[0],
    //     values
    //   );
    //   propertyInstance.name = enumRepresentation.classDefinition.name;
    //   propertyInstance.id = enumRepresentation.id;
    //   propertyInstances.push(propertyInstance);
    // }
    // for (const selectedVolunteer of this.selectedVolunteers) {
    //   const classInstance: ClassInstance = new ClassInstance(
    //     this.currentFormConfiguration.formEntry.classDefinitions[0],
    //     propertyInstances
    //   );
    //   classInstance.userId = selectedVolunteer.id;
    //   classInstance.tenantId = this.helpseeker.tenantId;
    //   classInstance.issuerId = this.helpseeker.id;
    //   classInstance.imagePath = this.currentFormConfiguration.formEntry.imagePath;
    //   classInstances.push(classInstance);
    // }
    // this.classInstanceService
    //   .createNewClassInstances(this.marketplace, classInstances)
    //   .toPromise()
    //   .then((ret: ClassInstance[]) => {
    //     // handle returned value if necessary
    //     if (!isNullOrUndefined(ret)) {
    //       this.returnedClassInstances.push(...ret);
    //       this.handleNextClick();
    //     }
    //   });
  }
}
