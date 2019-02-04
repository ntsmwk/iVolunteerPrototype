import { FusePropertyListModule } from './property-list.module';

describe('PropertyListModule', () => {
  let propertyListModule: FusePropertyListModule;

  beforeEach(() => {
    propertyListModule = new FusePropertyListModule();
  });

  it('should create an instance', () => {
    expect(propertyListModule).toBeTruthy();
  });
});
