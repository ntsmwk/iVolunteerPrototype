import { DialogFactoryModule } from './dialog-factory.module';

describe('DialogFactoryModule', () => {
  let dialogFactoryModule: DialogFactoryModule;

  beforeEach(() => {
    dialogFactoryModule = new DialogFactoryModule();
  });

  it('should create an instance', () => {
    expect(dialogFactoryModule).toBeTruthy();
  });
});
