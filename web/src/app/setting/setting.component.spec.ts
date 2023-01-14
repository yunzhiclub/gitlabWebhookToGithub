import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SettingComponent } from './setting.component';
import {SettingModule} from './setting.module';
import {RouterTestingModule} from '@angular/router/testing';

describe('LoginComponent', () => {
  let component: SettingComponent;
  let fixture: ComponentFixture<SettingComponent>;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        SettingModule,
        RouterTestingModule,
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SettingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
