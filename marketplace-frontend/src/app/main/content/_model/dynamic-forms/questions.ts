  export class QuestionBase<T> {
    value: T;
    key: string;
    label: string;
    required: boolean;
    order: number;
    controlType: string;

    //values?: T[];

    validations?: Validator[];
   
    constructor(options: {
        value?: T,
        key?: string,
        label?: string,
        required?: boolean,
        order?: number,
        controlType?: string,
        //values?: T[],
        
        validations?: Validator[]
      } = {}) {
      this.value = options.value;
      this.key = options.key || '';
      this.label = options.label || '';
      this.required = !!options.required;
      this.order = options.order === undefined ? 1 : options.order;
      this.controlType = options.controlType || '';
      //this.values = options.values || undefined;
      this.validations = options.validations || undefined;
      
    }
  }

  export interface Validator {
    name: string;
    validator: any;
    message: string;
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
    type: number;
    constructor(options: {} = {}) {
      super(options);
      this.type = options['type'] || '';
    }
  }

  export class DropdownQuestion extends QuestionBase<string> {
    controlType = 'dropdown';
    options: {key: string, value: string}[] = [];
    constructor(options: {} = {}) {
      super(options);
      this.options = options['options'] || [];
    }
  }

  export class NumberDropdownQuestion extends QuestionBase<number> {
    controlType = 'numberdropdown';
    options: {key: number, value: number}[] = [];
    constructor(options: {} = {}) {
      super(options);
      this.options = options['options'] || [];
    }
  }

  export class RadioButtonQuestion extends QuestionBase<string> {
    controlType = 'radiobutton'
    options: {key: string, value: string}[] = [];
    constructor(options: {} = {}) {
      super(options);
      this.options = options['options'] || [];
    }
  }

  export class DatepickerQuestion extends QuestionBase<Date> {
    controlType = 'datepicker'
    options: {}[] = [];
    constructor(options: {} = {}) {
      super(options);
      this.options = options['options'] || [];
    }
  }

  export class SliderQuestion extends QuestionBase<number> {
    controlType = 'slider'
    options: {}[] = [];
    min: number;
    max: number;

    constructor(options: {} = {}) {
      super(options);
      this.options = options['options'] || [];
    }
  }

  export class SlideToggleQuestion extends QuestionBase<boolean> {
    controlType = 'slidetoggle'
    options: {}[] = [];

    constructor(options: {} = {}) {
      super(options);
      this.options = options['options'] || [];
    }
  }

  export class DropdownMultipleQuestion extends QuestionBase<string> {
    controlType = 'dropdown-multiple'
    options: {key: string, value: string}[] = [];
    values: {key: string, value: string}[] = [];

    constructor(options: {} = {}) {
      super(options);
      this.options = options['options'] || [];
      this.values = options['values'] || [];
    }
  }

