import { PropertyListModule } from './property-list.module';

describe('PropertyListModule', () => {
  let propertyListModule: PropertyListModule;

  beforeEach(() => {
    propertyListModule = new PropertyListModule();
  });

  it('should create an instance', () => {
    expect(propertyListModule).toBeTruthy();
  });
});
