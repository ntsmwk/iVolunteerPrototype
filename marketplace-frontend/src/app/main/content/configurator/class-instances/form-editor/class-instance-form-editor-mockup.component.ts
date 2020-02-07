import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../_model/marketplace';
import { ClassDefinitionService } from '../../../_service/meta/core/class/class-definition.service';
import { ClassDefinition } from '../../../_model/meta/Class';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { QuestionService } from 'app/main/content/_service/question.service';
import { QuestionBase } from 'app/main/content/_model/dynamic-forms/questions';
import { FormControl, FormBuilder, FormGroup, FormArray } from '@angular/forms';
import { isNullOrUndefined } from 'util';
import { maxOtherNew } from '../../../_validator/custom.validators';


@Component({
  selector: 'app-class-instance-form-editor-mockup',
  templateUrl: './class-instance-form-editor-mockup.component.html',
  styleUrls: ['./class-instance-form-editor-mockup.component.scss'],
  providers: [QuestionService]
})
export class ClassInstanceFormEditorMockupComponent implements OnInit {

  marketplace: Marketplace;
  configurableClass: ClassDefinition;

  isLoaded: boolean = false;

  questions: QuestionBase<any>[];

  panelOpenState = false;


  //============DEMO
  flexProdDemoMode: boolean = false;
  demoClasses: ClassDefinition[] = [];
  demoClassIds: string[] = [
    'oteAllgemein_producer', 'oteMoeglicheVorbehandlung_producer', 'otecKonvektoren_producer', 'otecTragerahmen_producer',
    'otecZwischenrahmen_producer', 'otecKronenstoecke_producer', 'otecChargierkoerbe_producer', 'ofenBetrieblicheEigenschaften_producer',
    'ogeBaugroesse_producer', 'oqeQualitätsnormen_producer', 'oqeWartungen_producer', 'ingeBundabmessungen_producer', 'inqeMaterialart_producer',
    'outteMechanischeEigenschaften_producer', 'outteGefuege_producer', 'outgeMoeglicheBundabmessungen_producer',
    'outqeMaterialart_producer', 'logistischeBeschreibung_producer', 'preislicheBeschreibung_producer'
  ];

  demoClassPaths: string[] = [
    'Technische Beschreibung > Ofen > Technische Eigenschaften > Allgemein',
    'Technische Beschreibung > Ofen > Technische Eigenschaften > Mögliche Vorbehandlung',
    'Technische Beschreibung > Ofen > Technische Eigenschaften > Chargierhilfe > Konvektoren',
    'Technische Beschreibung > Ofen > Technische Eigenschaften > Chargierhilfe > Trägerrahmen',
    'Technische Beschreibung > Ofen > Technische Eigenschaften > Chargierhilfe > Zwischenrahmen',
    'Technische Beschreibung > Ofen > Technische Eigenschaften > Chargierhilfe > Kronenstöcke',
    'Technische Beschreibung > Ofen > Technische Eigenschaften > Chargierhilfe > Chargierkörbe',
    'Technische Beschreibung > Ofen > Betriebliche Eigenschaften',
    'Technische Beschreibung > Ofen > Geometrische Eigenschaften > Baugröße',
    'Technische Beschreibung > Ofen > Qualitative Eigenschaften > Qualitätsnormen',
    'Technische Beschreibung > Ofen > Qualitative Eigenschaften > Wartungen',
    'Technische Beschreibung > Input > Geometrische Eigenschaften > Bundabmessungen',
    'Technische Beschreibung > Input > Qualitative Eigenschaften > Materialart',
    'Technische Beschreibung > Output > Technische Eigenschaften > Mechanische Eigenschaften',
    'Technische Beschreibung > Output > Technische Eigenschaften > Gefüge',
    'Technische Beschreibung > Output > Geometrische Eigenschaften > Mögliche Bundabmessungen',
    'Technische Beschreibung > Output > Qualitative Eigenschaften > Materialart',
    'Logistische Beschreibung',
    'Preisliche Beschreibung'
  ];

  form: FormGroup;
  showMaxGluehtemperatur: boolean;
  //================

  constructor(private router: Router,
    private route: ActivatedRoute,
    private marketplaceService: CoreMarketplaceService,
    private classDefinitionService: ClassDefinitionService,
    private questionService: QuestionService,
    private fb: FormBuilder,
  ) {

  }

  ngOnInit() {
    let marketplaceId: string;
    let classId: string;
    Promise.all([
      this.route.params.subscribe(param => {
        console.log(param);
        console.log(param['marketplaceId']);

        marketplaceId = param['marketplaceId'];
        // this.showMaxGluehtemperatur = param['showMaxGluehtemperatur'];
        this.showMaxGluehtemperatur = true;
      }),
      this.route.queryParams.subscribe(queryParams => {
        console.log(queryParams);
        if (!isNullOrUndefined(queryParams[0])) {
          classId = queryParams[0];
        }
      })

    ]).then(() => {
      this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;
        this.classDefinitionService.getClassDefinitionById(this.marketplace, classId).toPromise().then((configurableClass: ClassDefinition) => {
          this.configurableClass = configurableClass;
          //TODO
          // this.questions = this.questionService.getQuestionsFromProperties(this.configurableClass.properties);
          if (classId === 'root_producer' || classId === 'root_consumer') {
            this.flexProdDemoMode = true;

            let finishedNumber = 0;
            for (let id of this.demoClassIds) {
              this.classDefinitionService.getClassDefinitionById(this.marketplace, id).toPromise().then((classDefinition: ClassDefinition) => {
                let index = this.demoClassIds.indexOf(id);
                this.demoClasses[index] = classDefinition;
                finishedNumber++;
                if (finishedNumber >= this.demoClassIds.length) {
                  this.createDemoFormControl();
                  this.isLoaded = true;
                }
              });
            }

          }

          // this.isLoaded = true;
          console.log(this.marketplace);
          console.log(this.configurableClass);
        });
      });
    });
  }

  createDemoFormControl() {
    this.fb = new FormBuilder();
    this.form = new FormGroup({
      "00": new FormControl(''),
      "01": new FormControl(''),
      "02": new FormControl(''),
      "03": new FormControl(''),
      "04": new FormControl(''),
      "05": new FormControl(''),

      "10": new FormControl(''),

      "chargierhilfe": new FormControl(''),

      "20": new FormControl({ value: '', disabled: false }),
      "21": new FormControl({ value: '', disabled: false }),
      "22": new FormControl({ value: '', disabled: false }),

      "30": new FormControl({ value: '', disabled: false }),
      "31": new FormControl({ value: '', disabled: false }),
      "32": new FormControl({ value: '', disabled: false }),

      "40": new FormControl({ value: '', disabled: false }),
      "41": new FormControl({ value: '', disabled: false }),
      "42": new FormControl({ value: '', disabled: false }),

      "50": new FormControl({ value: '', disabled: false }),
      "51": new FormControl({ value: '', disabled: false }),
      "52": new FormControl({ value: '', disabled: false }),

      "60": new FormControl({ value: '', disabled: false }),
      "61": new FormControl({ value: '', disabled: false }),
      "62": new FormControl({ value: '', disabled: false }),

      "70": new FormControl(''),
      "71": new FormControl(''),

      "80": new FormArray([]),
      "81": new FormControl(''),
      "82": new FormControl(''),

      "90": new FormControl(''),
      "91": new FormControl(''),

      "100": new FormControl(''),
      "101": new FormControl(''),

      "110": new FormControl({ value: '' }),
      "111": new FormControl({ value: '' }),
      "112": new FormControl(''),
      "113": new FormControl(''),

      "120": new FormControl(''),
      "121": new FormControl(''),

      "130": new FormControl(''),
      "131": new FormControl(''),
      "132": new FormControl(''),

      "140": new FormControl(''),

      "150": new FormControl(''),
      "151": new FormControl(''),
      "152": new FormControl(''),
      "153": new FormControl(''),

      "160": new FormControl(''),
      "161": new FormControl(''),

      "170": new FormControl(''),
      "171": new FormControl(''),
      "172": new FormControl(''),
      "173": new FormControl(''),
      "174": new FormControl(''),
      "175": new FormControl(''),
      "176": new FormControl(''),


      "180": new FormControl(''),

    });

    this.form.controls['04'].setValue(true);
    this.form.controls['120'].setValue(true);
    this.form.controls['160'].setValue(true);

    this.form.setValidators([maxOtherNew(this.form, "110", 'aussen'),
    maxOtherNew(this.form, "111", 'innen'),
    maxOtherNew(this.form, "150", 'aussen'),
    maxOtherNew(this.form, "151", 'innen')]);



    this.addBaugroesseInnenDurchmesserRow();

  }

  addBaugroesseInnenDurchmesserRow() {
    (this.form.controls['80'] as FormArray).push(new FormControl(''));
  }

  removeBaugroesseInnenDurchmesserRow(i: number) {
    (this.form.controls['80'] as FormArray).removeAt(i);
  }

  updateBauartDepenencies() {
    if (this.form.value['02'] == 'Band') {
      this.form.controls['20'].enable();
      this.form.controls['21'].enable();
      this.form.controls['22'].enable();

      this.form.controls['30'].enable();
      this.form.controls['31'].enable();
      this.form.controls['32'].enable();

      this.form.controls['40'].enable();
      this.form.controls['41'].enable();
      this.form.controls['42'].enable();

      this.form.controls['60'].enable();
      this.form.controls['61'].enable();
      this.form.controls['62'].enable();

    } else {
      this.form.controls['20'].disable();
      this.form.controls['21'].disable();
      this.form.controls['22'].disable();

      this.form.controls['30'].disable();
      this.form.controls['31'].disable();
      this.form.controls['32'].disable();

      this.form.controls['40'].disable();
      this.form.controls['41'].disable();
      this.form.controls['42'].disable();

    }

    if (this.form.value['02'] == 'Draht') {
      this.form.controls['50'].enable();
      this.form.controls['51'].enable();
      this.form.controls['52'].enable();

      this.form.controls['60'].enable();
      this.form.controls['61'].enable();
      this.form.controls['62'].enable();
    } else {
      this.form.controls['50'].disable();
      this.form.controls['51'].disable();
      this.form.controls['52'].disable();
    }
  }

  test() {
    console.log(this.form.value);



  }

  chargierhilfeChange() {

    if (!isNullOrUndefined(this.form) && this.form.value['chargierhilfe'].length <= 0) {
      this.form.controls['80'].enable();
      this.form.controls['81'].enable();
      this.form.controls['82'].enable();

    }

    if (!isNullOrUndefined(this.form) && this.form.value['chargierhilfe'].length > 0) {
      (this.form.controls['80'] as FormArray).reset();

      for (let i = 1; i < (this.form.controls['80'] as FormArray).length; i++) {
        (this.form.controls['80'] as FormArray).removeAt(i)
      }
      this.form.controls['80'].disable();

      this.form.controls['81'].disable();
      this.form.controls['81'].reset();
      this.form.controls['82'].disable();
      this.form.controls['81'].reset();

    }

  }

  resetBauartDependencies() {
    this.form.controls['chargierhilfe'].setValue([]);
    this.form.controls['20'].setValue('');
    this.form.controls['21'].setValue('');
    this.form.controls['22'].setValue('');
    this.form.controls['20'].markAsUntouched();
    this.form.controls['21'].markAsUntouched();
    this.form.controls['22'].markAsUntouched();

    this.form.controls['30'].setValue('');
    this.form.controls['31'].setValue('');
    this.form.controls['32'].setValue('');
    this.form.controls['30'].markAsUntouched();
    this.form.controls['31'].markAsUntouched();
    this.form.controls['32'].markAsUntouched();

    this.form.controls['40'].setValue('');
    this.form.controls['41'].setValue('');
    this.form.controls['42'].setValue('');
    this.form.controls['40'].markAsUntouched();
    this.form.controls['41'].markAsUntouched();
    this.form.controls['42'].markAsUntouched();

    this.form.controls['50'].setValue('');
    this.form.controls['51'].setValue('');
    this.form.controls['52'].setValue('');
    this.form.controls['50'].markAsUntouched();
    this.form.controls['51'].markAsUntouched();
    this.form.controls['52'].markAsUntouched();

    this.form.controls['60'].setValue('');
    this.form.controls['61'].setValue('');
    this.form.controls['62'].setValue('');
    this.form.controls['60'].markAsUntouched();
    this.form.controls['61'].markAsUntouched();
    this.form.controls['62'].markAsUntouched();
  }

  resetWalzDependencies() {
    this.form.controls['01'].setValue([]);
    this.form.controls['01'].markAsUntouched();
  }

  calculateLargestInnen() {

    let choices: number[] = [];

    choices.push(this.form.value['21']);
    choices.push(this.form.value['31']);
    choices.push(this.form.value['41']);
    choices.push(this.form.value['51']);
    choices.push(this.form.value['61']);

    for (let v of this.form.controls['80'].value) {
      choices.push(v);
    }

    return (Math.max(...choices));

  }

  calculateLargestAussen() {
    // Max(21, 31,41,51,61, 81)

    let choices: number[] = [];

    choices.push(this.form.value['21']);
    choices.push(this.form.value['31']);
    choices.push(this.form.value['41']);
    choices.push(this.form.value['51']);
    choices.push(this.form.value['61']);
    choices.push(this.form.value['81']);

    return Math.max(...choices);
  }


  inputBundabmessungenAussenError = false;
  inputBundabmessungenInnenError = false;
  outputBundabmessungenAussenError = false;
  outputBundabmessungenInnenError = false;

  checkInputBundabmessungenAussen() {
    let a = this.calculateLargestAussen();
    console.log(a);
    if (this.form.value['110'] > 100) {
      this.form.controls['110'].setErrors({ 'incorrect': true });
      this.inputBundabmessungenAussenError = true
      this.form.controls['110'].markAsTouched();

    }
  }



  checkInputBundabmessungenInnen() {
    if (this.form.value['111'] > this.calculateLargestInnen()) {
      this.form.controls['111'].setErrors({ 'incorrect': true });
      this.inputBundabmessungenInnenError = true
      this.form.controls['111'].markAsTouched();

    } else {
      this.inputBundabmessungenInnenError = false;
    }

  }


  chargierhilfeContains(value: string) {
    return (this.form.value['chargierhilfe'] as String[]).indexOf(value) !== -1;
  }

  displayErrorMessage(formControlName: string, key: string) {
    if (this.form.controls[formControlName].hasError('maxothernew') || this.form.controls[formControlName].hasError('incorrect_max')) {

      if (key == 'aussen') {
        return 'Außendurchmesser größer als Spezifikation (Baugröße / Chargierhilfe)';
      } else if (key == 'innen') {
        return 'Innendurchmesser größer als Spezifikation (Baugröße / Chargierhilfe)';
      }

    } else if (this.form.controls[formControlName].hasError('required')) {
      return 'This field is required';
    }
  }

  printAnything(anything: any) {
    console.log(anything);
  }


  returnPressed() {
    this.router.navigate([`/main/configurator`], { queryParams: { open: 'haubenofen' } });
  }


  navigateBack() {
    window.history.back();
  }

}