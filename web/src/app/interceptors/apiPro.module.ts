import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { NullOrUndefinedOrEmptyInterceptor } from "./null-or-undefined-or-empty.interceptor";
import { LoadingInterceptor } from "./LoadingInterceptor";
import { HttpErrorInterceptor } from "./http-error.interceptor";
import { NgModule } from "@angular/core";
import { ApiPrefixAndMergeMapInterceptor } from "./ApiPrefixAndMergeMapInterceptor";

@NgModule({
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: ApiPrefixAndMergeMapInterceptor,
    multi: true
  }, {
    provide: HTTP_INTERCEPTORS,
    useClass: NullOrUndefinedOrEmptyInterceptor,
    multi: true
  }, {
    provide: HTTP_INTERCEPTORS,
    useClass: LoadingInterceptor,
    multi: true
  }
  ]
})
export class ApiProModule {
}
