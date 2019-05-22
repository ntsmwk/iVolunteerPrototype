import { MultiplePropertyModule } from './multiple-property.module';

describe('MultiplePropertyModule', () => {
  let multiplePropertyModule: MultiplePropertyModule;

  beforeEach(() => {
    multiplePropertyModule = new MultiplePropertyModule();
  });

  it('should create an instance', () => {
    expect(multiplePropertyModule).toBeTruthy();
  });
});
