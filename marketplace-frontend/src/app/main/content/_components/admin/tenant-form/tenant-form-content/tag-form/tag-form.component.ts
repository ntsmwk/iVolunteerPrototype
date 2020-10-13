import { Component, OnInit, ViewChild, ElementRef, Output, EventEmitter, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { TenantTagService } from 'app/main/content/_service/tenant-tag.service';
import { isNullOrUndefined } from 'util';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';

@Component({
  selector: "tenant-tag-form",
  templateUrl: 'tag-form.component.html',
  styleUrls: ['./tag-form.component.scss']
})
export class TenantTagFormComponent implements OnInit {

  loaded: boolean;
  filteredTags: Observable<string[]>;
  possibleTags: string[] = [];
  @Input() addedTags: string[] = [];
  tagForm: FormGroup;

  @ViewChild('inputText', { static: false }) inputTextField: ElementRef;
  @Output() tags: EventEmitter<String[]> = new EventEmitter();

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private tenantTagService: TenantTagService,
  ) { }

  async ngOnInit() {
    this.loaded = false;
    this.possibleTags = <string[]>await this.tenantTagService.findAllAsString().toPromise();

    this.tagForm = this.formBuilder.group({
      inputText: new FormControl(''),
    });

    this.resetFilter();
    this.loaded = true;
  }

  handleTagAdd() {
    const inputText = this.tagForm.value.inputText;

    if (isNullOrUndefined(inputText)) { return; }

    const i = this.possibleTags.findIndex(t => t === inputText);
    const j = this.addedTags.findIndex(t => t === inputText);

    if (i <= -1 || j >= 0) { return; }

    this.addedTags.push(inputText);
    this.possibleTags = this.possibleTags.filter(t => t !== inputText);
    this.resetFilter();

    this.inputTextField.nativeElement.blur();
    this.tagForm.reset();
    this.emitChanges();

  }

  handleTagRemove(tag: string) {
    this.addedTags = this.addedTags.filter(t => t !== tag);
    this.possibleTags.push(tag);
    this.resetFilter();
    this.emitChanges();

  }

  private resetFilter() {
    this.filteredTags = this.tagForm.controls.inputText.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
  }

  private _filter(value: string): string[] {

    if (isNullOrUndefined(value)) { return; }

    const filterValue = value.toLowerCase();

    return this.possibleTags.filter(option => option.toLowerCase().includes(filterValue));
  }

  private emitChanges() {
    this.tags.emit(this.addedTags);
  }

}
