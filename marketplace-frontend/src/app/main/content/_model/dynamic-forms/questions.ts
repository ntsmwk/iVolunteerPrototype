import { ValidatorFn } from '@angular/forms';

export class QuestionBase<T> {
  value: T;
  key: string;
  label: string;
  order: number;
  controlType: string;
  required: boolean;

  values: T[];

  validators?: ValidatorFn[];
  messages: Map<string, string>;

  subQuestions?: QuestionBase<T>[];

  constructor(options: {
    value?: T,
    key?: string,
    label?: string,
    order?: number,
    controlType?: string,
    required?: boolean,
    values?: T[],

    validators?: ValidatorFn[],
    messages?: Map<string, string>
    subQuestions?: QuestionBase<T>[]
  } = {}) {
    this.value = options.value;
    this.key = options.key || '';
    this.label = options.label || '';
    this.order = options.order === undefined ? 1 : options.order;
    this.controlType = options.controlType || '';
    this.values = options.values || undefined;
    this.validators = options.validators || undefined;
    this.required = options.required || false;
    this.messages = options.messages || undefined;
    this.subQuestions = options.subQuestions || undefined;
  }
}


export class TextboxQuestion extends QuestionBase<string> {
  controlType = 'textbox';
  type: string;

  constructor(options: {} = {}) {
    super(options);
    this.type = options['type'] || '';
  }
}

export class TextAreaQuestion extends QuestionBase<string> {
  controlType = 'textarea';
  type: string;

  constructor(options: {} = {}) {
    super(options);
    this.type = options['type'] || '';
  }
}

export class NumberBoxQuestion extends QuestionBase<number> {
  controlType = 'numberbox';
  type = 'number';
  constructor(options: {} = {}) {
    super(options);
    this.type = options['type'] || '';
  }
}

export class DropdownQuestion extends QuestionBase<string> {
  controlType = 'dropdown';
  options: { key: string, value: string }[] = [];
  constructor(options: {} = {}) {
    super(options);
    this.options = options['options'] || [];
  }
}

export class NumberDropdownQuestion extends QuestionBase<number> {
  controlType = 'numberdropdown';
  options: { key: number, value: number }[] = [];
  constructor(options: {} = {}) {
    super(options);
    this.options = options['options'] || [];
  }
}

export class RadioButtonQuestion extends QuestionBase<string> {
  controlType = 'radiobutton';
  options: { key: string, value: string }[] = [];
  constructor(options: {} = {}) {
    super(options);
    this.options = options['options'] || [];
  }
}

export class DatepickerQuestion extends QuestionBase<Date> {
  controlType = 'datepicker';
  options: {}[] = [];
  constructor(options: {} = {}) {
    super(options);
    this.options = options['options'] || [];
  }
}

export class SliderQuestion extends QuestionBase<number> {
  controlType = 'slider';
  options: {}[] = [];
  min: number;
  max: number;

  constructor(options: {} = {}) {
    super(options);
    this.options = options['options'] || [];
  }
}

export class SlideToggleQuestion extends QuestionBase<boolean> {
  controlType = 'slidetoggle';
  options: {}[] = [];

  constructor(options: {} = {}) {
    super(options);
    this.options = options['options'] || [];
  }
}

export class DropdownMultipleQuestion extends QuestionBase<string> {
  controlType = 'dropdown-multiple';
  options: { key: string, value: string }[] = [];
  values: any[] = [];

  constructor(options: {} = {}) {
    super(options);
    this.options = options['options'] || [];
    this.values = options['values'] || [];
  }
}

export class MultipleQuestion extends QuestionBase<string> {
  controlType = 'multiple';
  options: { key: string, value: string }[] = [];
  // values: {key: string, value: string}[] = [];
  // subQuestions: QuestionBase<any>[] = [];

  constructor(options: {} = {}) {
    super(options);
    this.options = options['options'] || [];
    // this.values = options['values'] || [];
    // this.subQuestions = options['subQuestions'] || [];
  }
}

export class GenericQuestion extends QuestionBase<string> {
  controlType = 'generic';
  options: { key: string, value: string }[] = [];

  constructor(options: {} = {}) {
    super(options);
    this.options = options['options'] || [];
  }
}

// export class SingleSelectionEnumQuestion extends QuestionBase<any> {
//   controlType = 'enum-single';
//   options: EnumEntry[] = [];

//   // +++ TODO
//   constructor(options: {} = {}) {
//     super(options);
//     this.options = options['options'] || [];
//   }
// }

// export class MultipleSelectionEnumQuestion extends QuestionBase<any> {
//   controlType = 'enum-multiple';
//   options: EnumEntry[] = [];

//   // +++ TODO
//   constructor(options: {} = {}) {
//     super(options);
//     this.options = options['options'] || [];
//   }
// }

export class TupleDropdownQuestion extends QuestionBase<any> {
  controlType = 'tuple';
  options: { id: any, label: any }[] = [];

  constructor(options: {} = {}) {
    super(options);
    this.options = options['options'] || [];
  }
}

