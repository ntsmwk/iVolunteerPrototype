// import { ValidatorFn } from '@angular/forms';
// import { TreePropertyEntry } from '../meta/property/tree-property';
// import { Location } from '../meta/property/location';
// import { PropertyType } from '../meta/property/property';

// export class DynamicFormItemBase<T> {
//   value: T;
//   key: string;
//   label: string;
//   order: number;
//   controlType: string;
//   required: boolean;

//   values: T[];

//   validators?: ValidatorFn[];
//   messages: Map<string, string>;

//   subItems?: DynamicFormItemBase<T>[];

//   constructor(options: {
//     value?: T,
//     key?: string,
//     label?: string,
//     order?: number,
//     controlType?: string,
//     required?: boolean,
//     values?: T[],

//     validators?: ValidatorFn[],
//     messages?: Map<string, string>
//     subItems?: DynamicFormItemBase<T>[]
//   } = {}) {
//     this.value = options.value;
//     this.key = options.key || '';
//     this.label = options.label || '';
//     this.order = options.order === undefined ? 1 : options.order;
//     this.controlType = options.controlType || '';
//     this.values = options.values || undefined;
//     this.validators = options.validators || undefined;
//     this.required = options.required || false;
//     this.messages = options.messages || undefined;
//     this.subItems = options.subItems || undefined;
//   }
// }


// export class TextboxFormItem extends DynamicFormItemBase<string> {
//   controlType = 'textbox';
//   type: string;

//   constructor(options: {} = {}) {
//     super(options);
//     this.type = options['type'] || '';
//   }
// }

// export class TextAreaFormItem extends DynamicFormItemBase<string> {
//   controlType = 'textarea';
//   type: string;

//   constructor(options: {} = {}) {
//     super(options);
//     this.type = options['type'] || '';
//   }
// }

// export class NumberBoxFormItem extends DynamicFormItemBase<number> {
//   controlType = 'numberbox';
//   type = 'number';
//   constructor(options: {} = {}) {
//     super(options);
//     this.type = options['type'] || '';
//   }
// }

// export class DropdownFormItem extends DynamicFormItemBase<string> {
//   controlType = 'dropdown';
//   options: { key: string, value: string }[] = [];
//   constructor(options: {} = {}) {
//     super(options);
//     this.options = options['options'] || [];
//   }
// }

// export class NumberDropdownFormItem extends DynamicFormItemBase<number> {
//   controlType = 'numberdropdown';
//   options: { key: number, value: number }[] = [];
//   constructor(options: {} = {}) {
//     super(options);
//     this.options = options['options'] || [];
//   }
// }

// export class RadioButtonFormItem extends DynamicFormItemBase<string> {
//   controlType = 'radiobutton';
//   options: { key: string, value: string }[] = [];
//   constructor(options: {} = {}) {
//     super(options);
//     this.options = options['options'] || [];
//   }
// }

// export class DatepickerFormItem extends DynamicFormItemBase<Date> {
//   controlType = 'datepicker';
//   options: {}[] = [];
//   constructor(options: {} = {}) {
//     super(options);
//     this.options = options['options'] || [];
//   }
// }

// export class SliderFormItem extends DynamicFormItemBase<number> {
//   controlType = 'slider';
//   options: {}[] = [];
//   min: number;
//   max: number;

//   constructor(options: {} = {}) {
//     super(options);
//     this.options = options['options'] || [];
//   }
// }

// export class SlideToggleFormItem extends DynamicFormItemBase<boolean> {
//   controlType = 'slidetoggle';
//   options: {}[] = [];

//   constructor(options: {} = {}) {
//     super(options);
//     this.options = options['options'] || [];
//   }
// }

// export class DropdownMultipleFormItem extends DynamicFormItemBase<string> {
//   controlType = 'dropdown-multiple';
//   options: { key: string, value: string }[] = [];
//   values: any[] = [];

//   constructor(options: {} = {}) {
//     super(options);
//     this.options = options['options'] || [];
//     this.values = options['values'] || [];
//   }
// }

// export class MultipleFormItem extends DynamicFormItemBase<string> {
//   controlType = 'multiple';
//   options: { key: string, value: string }[] = [];
//   // values: {key: string, value: string}[] = [];
//   // subQuestions: DynamicFormItemBase<any>[] = [];

//   constructor(options: {} = {}) {
//     super(options);
//     this.options = options['options'] || [];
//     // this.values = options['values'] || [];
//     // this.subQuestions = options['subQuestions'] || [];
//   }
// }

// export class GenericFormItem extends DynamicFormItemBase<string> {
//   controlType = 'generic';
//   options: { key: string, value: string }[] = [];

//   constructor(options: {} = {}) {
//     super(options);
//     this.options = options['options'] || [];
//   }
// }

// export class SingleSelectionTreeFormItem extends DynamicFormItemBase<any> {
//   controlType = 'tree-single-select';
//   options: TreePropertyEntry[] = [];

//   // +++ TODO
//   constructor(options: {} = {}) {
//     super(options);
//     this.options = options['options'] || [];
//   }
// }

// export class MultipleSelectionTreeFormItem extends DynamicFormItemBase<any> {
//   controlType = 'tree-multiple-select';
//   options: TreePropertyEntry[] = [];

//   // +++ TODO
//   constructor(options: {} = {}) {
//     super(options);
//     this.options = options['options'] || [];
//   }
// }

// export class TupleDropdownFormItem extends DynamicFormItemBase<any> {
//   controlType = 'tuple';
//   options: { id: any, label: any }[] = [];

//   constructor(options: {} = {}) {
//     super(options);
//     this.options = options['options'] || [];
//   }
// }

// export class LocationFormItem extends DynamicFormItemBase<Location> {
//   controlType = 'location';
//   constructor(options: {} = {}) {
//     super(options);
//   }
// }

// export class ComputedFormItem extends DynamicFormItemBase<string> {
//   controlType = 'computed';
//   returnType: PropertyType;

//   constructor(options: {} = {}) {
//     super(options);
//     this.returnType = options['returnType'] || [];

//   }
// }

