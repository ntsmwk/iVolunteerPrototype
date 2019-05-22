import { SinglePropertyModule } from './single-property.module';

describe('SinglePropertyModule', () => {
  let singlePropertyModule: SinglePropertyModule;

  beforeEach(() => {
    singlePropertyModule = new SinglePropertyModule();
  });

  it('should create an instance', () => {
    expect(singlePropertyModule).toBeTruthy();
  });
});
