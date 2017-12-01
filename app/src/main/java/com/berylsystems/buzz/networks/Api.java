package com.berylsystems.buzz.networks;


import com.berylsystems.buzz.networks.api_request.RequestBasic;
import com.berylsystems.buzz.networks.api_request.RequestCompanyAdditional;
import com.berylsystems.buzz.networks.api_request.RequestCompanyAuthenticate;
import com.berylsystems.buzz.networks.api_request.RequestCompanyDetails;
import com.berylsystems.buzz.networks.api_request.RequestCompanyGst;
import com.berylsystems.buzz.networks.api_request.RequestCompanyLogin;
import com.berylsystems.buzz.networks.api_request.RequestCompanyLogo;
import com.berylsystems.buzz.networks.api_request.RequestCompanySignature;
import com.berylsystems.buzz.networks.api_request.RequestCreateAccount;
import com.berylsystems.buzz.networks.api_request.RequestCreateAccountGroup;
import com.berylsystems.buzz.networks.api_request.RequestCreateBankCashDeposit;
import com.berylsystems.buzz.networks.api_request.RequestCreateBankCashWithdraw;
import com.berylsystems.buzz.networks.api_request.RequestCreateBillSundry;
import com.berylsystems.buzz.networks.api_request.RequestCreateCompany;
import com.berylsystems.buzz.networks.api_request.RequestCreateEXpence;
import com.berylsystems.buzz.networks.api_request.RequestCreateIncome;
import com.berylsystems.buzz.networks.api_request.RequestCreateMaterialCentre;
import com.berylsystems.buzz.networks.api_request.RequestCreateMaterialCentreGroup;
import com.berylsystems.buzz.networks.api_request.RequestCreatePurchase;
import com.berylsystems.buzz.networks.api_request.RequestCreateSaleVoucher;
import com.berylsystems.buzz.networks.api_request.RequestCreateUnit;
import com.berylsystems.buzz.networks.api_request.RequestCreateUnitConversion;
import com.berylsystems.buzz.networks.api_request.RequestEditLogin;
import com.berylsystems.buzz.networks.api_request.RequestForgotPassword;
import com.berylsystems.buzz.networks.api_request.RequestLoginEmail;
import com.berylsystems.buzz.networks.api_request.RequestNewPassword;
import com.berylsystems.buzz.networks.api_request.RequestPlan;
import com.berylsystems.buzz.networks.api_request.RequestRegister;
import com.berylsystems.buzz.networks.api_request.RequestResendOtp;
import com.berylsystems.buzz.networks.api_request.RequestUpdateMobileNumber;
import com.berylsystems.buzz.networks.api_request.RequestVerification;
import com.berylsystems.buzz.networks.api_response.salevoucher.CreateSaleVoucherResponse;
import com.berylsystems.buzz.networks.api_response.bankcashwithdraw.CreateBankCashWithdrawResponse;
import com.berylsystems.buzz.networks.api_response.bankcashwithdraw.DeleteBankCashWithdrawResponse;
import com.berylsystems.buzz.networks.api_response.bankcashwithdraw.EditBankCashWithdrawResponse;
import com.berylsystems.buzz.networks.api_response.bankcashwithdraw.GetBankCashWithdrawDetailsResponse;
import com.berylsystems.buzz.networks.api_response.bankcashwithdraw.GetBankCashWithdrawResponse;
import com.berylsystems.buzz.networks.api_response.expence.CreateExpenceResponse;
import com.berylsystems.buzz.networks.api_response.expence.DeleteExpenceResponse;
import com.berylsystems.buzz.networks.api_response.expence.EditExpenceResponse;
import com.berylsystems.buzz.networks.api_response.expence.GetExpenceDetailsResponse;
import com.berylsystems.buzz.networks.api_response.expence.GetExpenceResponse;
import com.berylsystems.buzz.networks.api_response.purchase.CreatePurchaseResponce;
import com.berylsystems.buzz.networks.api_response.income.DeleteIncomeResponse;
import com.berylsystems.buzz.networks.api_response.income.EditIncomeResponse;
import com.berylsystems.buzz.networks.api_response.income.GetIncomeDetailsResponse;
import com.berylsystems.buzz.networks.api_response.income.GetIncomeResponse;
import com.berylsystems.buzz.networks.api_response.income.CreateIncomeResponse;
import com.berylsystems.buzz.networks.api_response.account.CreateAccountResponse;
import com.berylsystems.buzz.networks.api_response.account.DeleteAccountResponse;
import com.berylsystems.buzz.networks.api_response.account.EditAccountResponse;
import com.berylsystems.buzz.networks.api_response.account.GetAccountDetailsResponse;
import com.berylsystems.buzz.networks.api_response.account.GetAccountResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.CreateAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.DeleteAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.EditAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.GetAccountGroupDetailsResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.GetAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.bankcashdeposit.CreateBankCashDepositResponse;
import com.berylsystems.buzz.networks.api_response.bill_sundry.CreateBillSundryResponse;
import com.berylsystems.buzz.networks.api_response.bill_sundry.DeleteBillSundryResponse;
import com.berylsystems.buzz.networks.api_response.bill_sundry.EditBillSundryResponse;
import com.berylsystems.buzz.networks.api_response.bill_sundry.GetBillSundryDetailsResponse;
import com.berylsystems.buzz.networks.api_response.bill_sundry.GetBillSundryListResponse;
import com.berylsystems.buzz.networks.api_response.bill_sundry.GetBillSundryNatureResponse;
import com.berylsystems.buzz.networks.api_response.company.CompanyAuthenticateResponse;
import com.berylsystems.buzz.networks.api_response.company.CompanyListResponse;
import com.berylsystems.buzz.networks.api_response.company.CreateCompanyResponse;
import com.berylsystems.buzz.networks.api_response.company.DeleteCompanyResponse;
import com.berylsystems.buzz.networks.api_response.company.IndustryTypeResponse;
import com.berylsystems.buzz.networks.api_response.companylogin.CompanyLoginResponse;
import com.berylsystems.buzz.networks.api_response.companylogin.CompanyUserResponse;
import com.berylsystems.buzz.networks.api_response.getcompany.CompanyResponse;
import com.berylsystems.buzz.networks.api_response.item.CreateItemResponse;
import com.berylsystems.buzz.networks.api_response.item.DeleteItemResponse;
import com.berylsystems.buzz.networks.api_response.item.EditItemResponse;
import com.berylsystems.buzz.networks.api_response.item.GetItemDetailsResponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.CreateMaterialCentreResponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.DeleteMaterialCentreResponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.EditMaterialCentreReponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.GetMaterialCentreDetailResponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.GetMaterialCentreListResponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.StockResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.CreateMaterialCentreGroupResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.DeleteMaterialCentreGroupResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.EditMaterialCentreGroupResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.GetMaterialCentreGroupDetailResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.GetMaterialCentreGroupListResponse;
import com.berylsystems.buzz.networks.api_response.otp.OtpResponse;
import com.berylsystems.buzz.networks.api_response.packages.GetPackageResponse;
import com.berylsystems.buzz.networks.api_response.packages.PlanResponse;
import com.berylsystems.buzz.networks.api_response.purchasetype.GetPurchaseTypeResponse;
import com.berylsystems.buzz.networks.api_response.saletype.GetSaleTypeResponse;
import com.berylsystems.buzz.networks.api_response.salevoucher.GetSaleVoucherListResponse;
import com.berylsystems.buzz.networks.api_response.taxcategory.GetTaxCategoryResponse;
import com.berylsystems.buzz.networks.api_response.unit.GetUqcResponse;
import com.berylsystems.buzz.networks.api_response.user.UserApiResponse;
import com.berylsystems.buzz.networks.api_response.userexist.UserExistResponse;
import com.berylsystems.buzz.networks.api_response.unit.CreateUnitResponse;
import com.berylsystems.buzz.networks.api_response.unit.DeleteUnitResponse;
import com.berylsystems.buzz.networks.api_response.unit.EditUnitResponse;
import com.berylsystems.buzz.networks.api_response.unit.GetUnitDetailsResponse;
import com.berylsystems.buzz.networks.api_response.unit.GetUnitListResponse;
import com.berylsystems.buzz.networks.api_response.unitconversion.CreateUnitConversionResponse;
import com.berylsystems.buzz.networks.api_response.unitconversion.DeleteUnitConversionResponse;
import com.berylsystems.buzz.networks.api_response.unitconversion.EditUnitConversionResponse;
import com.berylsystems.buzz.networks.api_response.unitconversion.GetUnitConversionDetailsResponse;
import com.berylsystems.buzz.networks.api_response.unitconversion.GetUnitConversionListResponse;
import com.berylsystems.buzz.networks.api_response.item.GetItemResponse;
import com.berylsystems.buzz.networks.api_response.itemgroup.CreateItemGroupResponse;
import com.berylsystems.buzz.networks.api_response.itemgroup.DeleteItemGroupReponse;
import com.berylsystems.buzz.networks.api_response.itemgroup.EditItemGroupResponse;
import com.berylsystems.buzz.networks.api_response.itemgroup.GetItemGroupDetailsResponse;
import com.berylsystems.buzz.networks.api_response.itemgroup.GetItemGroupResponse;
import com.berylsystems.buzz.networks.api_request.RequestCreateItem;
import com.berylsystems.buzz.networks.api_request.RequestCreateItemGroup;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.berylsystems.buzz.networks.api_response.bankcashdeposit.DeleteBankCashDepositResponse;
import com.berylsystems.buzz.networks.api_response.bankcashdeposit.EditBankCashDepositResponse;
import com.berylsystems.buzz.networks.api_response.bankcashdeposit.GetBankCashDepositDetailsResponse;
import com.berylsystems.buzz.networks.api_response.bankcashdeposit.GetBankCashDepositResponse;

public interface Api {
    @POST("signup")
    Call<UserApiResponse> register(@Body RequestRegister payload);

    @PATCH("forgot_password")
    Call<UserApiResponse> forgotpassword(@Body RequestForgotPassword payload);

    @POST("verify_otp")
    Call<UserApiResponse> verifyotp(@Body RequestVerification payload);

    @PATCH("update_user")
    Call<UserApiResponse> newpassword(@Body RequestNewPassword payload);

    @PATCH("resend_otp")
    Call<OtpResponse> resendotp(@Body RequestResendOtp payload);

    @POST("login")
    Call<UserApiResponse> login(@Body RequestLoginEmail payload);

    @PATCH("update_user")
    Call<UserApiResponse> updatemobile(@Body RequestUpdateMobileNumber payload);

    @GET("check_exist")
    Call<UserExistResponse> exist(@Query("fb_id") String fb_id);

    @POST("company")
    Call<CreateCompanyResponse> createcompany(@Body RequestCreateCompany payload);

    @PATCH("company/{id}")
    Call<CreateCompanyResponse> cbasiccompany(@Body RequestBasic payload, @Path("id") String id);

    @PATCH("company/{id}")
    Call<CreateCompanyResponse> cdetails(@Body RequestCompanyDetails payload, @Path("id") String id);

    @PATCH("company/{id}")
    Call<CreateCompanyResponse> cgst(@Body RequestCompanyGst payload, @Path("id") String id);

    @PATCH("company/{id}")
    Call<CreateCompanyResponse> cadditional(@Body RequestCompanyAdditional payload, @Path("id") String id);

    @PATCH("company/{id}")
    Call<CreateCompanyResponse> clogo(@Body RequestCompanyLogo payload, @Path("id") String id);

    @PATCH("company/{id}")
    Call<CreateCompanyResponse> csignature(@Body RequestCompanySignature payload, @Path("id") String id);

    @PATCH("company/{id}")
    Call<CreateCompanyResponse> ceditlogin(@Body RequestEditLogin payload, @Path("id") String id);

    @POST("companylogin/{id}")
    Call<CompanyLoginResponse> clogin(@Body RequestCompanyLogin payload, @Path("id") String id);

    @GET("get_company/{id}")
    Call<CompanyListResponse> getCompanyList(@Path("id") String id);

    @DELETE("company/{id}")
    Call<DeleteCompanyResponse> cdelete(@Path("id") String id);

    @GET("get_industry")
    Call<IndustryTypeResponse> getIndustry();

    @POST("company_auth/{id}")
    Call<CompanyAuthenticateResponse> cauthenticate(@Body RequestCompanyAuthenticate payload, @Path("id") String id);

    @GET("company/{id}")
    Call<CompanyResponse> getcompany(@Path("id") String id);

    @GET("company/{id}")
    Call<CompanyResponse> searchcompany(@Path("id") String id);

    @GET("companyusers/{id}")
    Call<CompanyUserResponse> getcompanyusers(@Path("id") String id);

    @POST("create_account_group")
    Call<CreateAccountGroupResponse> createaccountgroup(@Body RequestCreateAccountGroup payload);

    @GET("account_groups/{id}")
    Call<GetAccountGroupResponse> getaccountgroup(@Path("id") String id);

    @GET("account_groups_details/{id}")
    Call<GetAccountGroupDetailsResponse> getaccountgroupdetails(@Path("id") String id);

    @PATCH("account_groups/{id}")
    Call<EditAccountGroupResponse> editaccountgroup(@Body RequestCreateAccountGroup payload, @Path("id") String id);

    @POST("account")
    Call<CreateAccountResponse> createaccount(@Body RequestCreateAccount payload);

    @GET("account/{id}")
    Call<GetAccountResponse> getaccount(@Path("id") String id, @Query("account_master_group") String account_master_group);

    @GET("account_detail/{id}")
    Call<GetAccountDetailsResponse> getaccountdetails(@Path("id") String id);

    @PATCH("account/{id}")
    Call<EditAccountResponse> editaccount(@Body RequestCreateAccount payload, @Path("id") String id);

    @DELETE("account/{id}")
    Call<DeleteAccountResponse> deleteaccount(@Path("id") String id);

    @DELETE("account_groups/{id}")
    Call<DeleteAccountGroupResponse> deleteaccountgroup(@Path("id") String id);

    @GET("getplan")
    Call<GetPackageResponse> getpackage();

    @POST("user_plan")
    Call<PlanResponse> plan(@Body RequestPlan payload);

    @GET("company_material_center_groups/{id}")
    Call<GetMaterialCentreGroupListResponse> getmaterialcentregrouplist(@Path("id") String id);

    @POST("material_center_group")
    Call<CreateMaterialCentreGroupResponse> creatematerialcentregroup(@Body RequestCreateMaterialCentreGroup payload);

    @PATCH("material_center_group/{id}")
    Call<EditMaterialCentreGroupResponse> editmaterialcentregroup(@Body RequestCreateMaterialCentreGroup payload, @Path("id") String id);

    @DELETE("material_center_group/{id}")
    Call<DeleteMaterialCentreGroupResponse> deletematerialcentregroup(@Path("id") String id);

    @GET("material_center_group/{id}")
    Call<GetMaterialCentreGroupDetailResponse> getmaterialcentregroupdetails(@Path("id") String id);

    @GET("company_material_center/{id}")
    Call<GetMaterialCentreListResponse> getmaterialcentrelist(@Path("id") String id);

    @POST("material_center")
    Call<CreateMaterialCentreResponse> creatematerialcentre(@Body RequestCreateMaterialCentre payload);

    @PATCH("material_center/{id}")
    Call<EditMaterialCentreReponse> editmaterialcentre(@Body RequestCreateMaterialCentre payload, @Path("id") String id);

    @DELETE("material_center/{id}")
    Call<DeleteMaterialCentreResponse> deletematerialcentre(@Path("id") String id);

    @GET("material_center/{id}")
    Call<GetMaterialCentreDetailResponse> getmaterialcentredetails(@Path("id") String id);

    @GET("stock_account/{id}")
    Call<StockResponse> getstock(@Path("id") String id);

    @GET("uqc_details")
    Call<GetUqcResponse> getuqc();

    @GET("company_item_units/{id}")
    Call<GetUnitListResponse> getunitlist(@Path("id") String id);

    @POST("item_unit")
    Call<CreateUnitResponse> createunit(@Body RequestCreateUnit payload);

    @PATCH("item_unit/{id}")
    Call<EditUnitResponse> editunit(@Body RequestCreateUnit payload, @Path("id") String id);

    @DELETE("item_unit/{id}")
    Call<DeleteUnitResponse> deleteunit(@Path("id") String id);

    @GET("item_unit/{id}")
    Call<GetUnitDetailsResponse> getunitdetails(@Path("id") String id);

    @GET("company_unit_conversion/{id}")
    Call<GetUnitConversionListResponse> getunitconversionlist(@Path("id") String id);

    @POST("unit_conversion")
    Call<CreateUnitConversionResponse> createunitconversion(@Body RequestCreateUnitConversion payload);

    @PATCH("unit_conversion/{id}")
    Call<EditUnitConversionResponse> editunitconversion(@Body RequestCreateUnitConversion payload, @Path("id") String id);

    @DELETE("unit_conversion/{id}")
    Call<DeleteUnitConversionResponse> deleteunitconversion(@Path("id") String id);

    @GET("unit_conversion/{id}")
    Call<GetUnitConversionDetailsResponse> getunitconversiondetails(@Path("id") String id);

    @GET("item/{id}")
    Call<GetItemResponse> getitem(@Path("id") String id);

    @POST("item")
    Call<CreateItemResponse> createitem(@Body RequestCreateItem payload);

    @DELETE("item/{id}")
    Call<DeleteItemResponse> deleteitem(@Path("id") String id);

    @PATCH("item/{id}")
    Call<EditItemResponse> edititem(@Body RequestCreateItem payload, @Path("id") String id);

    @GET("item_detail/{id}")
    Call<GetItemDetailsResponse> getitemdetails(@Path("id") String id);

    @GET("company_item_groups/{id}")
    Call<GetItemGroupResponse> getitemgroup(@Path("id") String id);

    @POST("item_group")
    Call<CreateItemGroupResponse> createitemgroup(@Body RequestCreateItemGroup payload);

    @DELETE("item_group/{id}")
    Call<DeleteItemGroupReponse> deleteitemgroup(@Path("id") String id);

    @GET("item_group/{id}")
    Call<GetItemGroupDetailsResponse> getitemgroupdetails(@Path("id") String id);

    @PATCH("item_group/{id}")
    Call<EditItemGroupResponse> edititemgroup(@Body RequestCreateItemGroup payload, @Path("id") String id);

    @GET("get_bill_sundry/{id}")
    Call<GetBillSundryListResponse> getbillsundrylist(@Path("id") String id);

    @POST("create_bill_sundry/{id}")
    Call<CreateBillSundryResponse> createbillsundry(@Body RequestCreateBillSundry payload, @Path("id") String id);

    @DELETE("delete_bill_sundry/{id}")
    Call<DeleteBillSundryResponse> deletebillsundry(@Path("id") String id);

    @GET("bill_sundry_info/{id}")
    Call<GetBillSundryDetailsResponse> getbillsundrydetails(@Path("id") String id);

    @PATCH("edit_bill_sundry/{id}")
    Call<EditBillSundryResponse> editbillsundry(@Body RequestCreateBillSundry payload, @Path("id") String id);

    @GET("bill_sundry_nature")
    Call<GetBillSundryNatureResponse> getbillsundrynature();

    @GET("purchase_type")
    Call<GetPurchaseTypeResponse> getpurchasetype();

    @GET("sale_type")
    Call<GetSaleTypeResponse> getsaletype();

    @GET("tax_category")
    Call<GetTaxCategoryResponse> gettaxcategory();

    @POST("sale_voucher/{id}")
    Call<CreateSaleVoucherResponse> createSaleVoucher(@Body RequestCreateSaleVoucher createItem, @Path("id") String id);

    @GET("company_sale_vouchers/{id}")
    Call<GetSaleVoucherListResponse> getsalevoucherlist(@Path("id") String id);

    @GET("company_bank_cash_deposits/{id}")
    Call<GetBankCashDepositResponse> getbankcashdeposit(@Path("id") String id);

    @DELETE("bank_cash_deposits/{id}")
    Call<DeleteBankCashDepositResponse> deletebankcashdeposit(@Path("id") String id);

    @GET("bank_cash_deposits/{id}")
    Call<GetBankCashDepositDetailsResponse> getbankcashdepositdetails(@Path("id") String id);

    @PATCH("bank_cash_deposits/{id}")
    Call<EditBankCashDepositResponse> editbankcashdeposit(@Body RequestCreateBankCashDeposit payload, @Path("id") String id);

    @POST("bank_cash_deposits")
    Call<CreateBankCashDepositResponse> createbankcashdeposit(@Body RequestCreateBankCashDeposit payload);

    @POST("bank_cash_withdraw")
    Call<CreateBankCashWithdrawResponse> createbankcashwithdraw(@Body RequestCreateBankCashWithdraw payload);

    @GET("company_bank_cash_withdraw/{id}")
    Call<GetBankCashWithdrawResponse> getbankcashwithdraw(@Path("id") String id);

    @DELETE("bank_cash_withdraw/{id}")
    Call<DeleteBankCashWithdrawResponse> deletebankcashwithdraw(@Path("id") String id);

    @GET("bank_cash_withdraw/{id}")
    Call<GetBankCashWithdrawDetailsResponse> getbankcashwithdrawdetails(@Path("id") String id);

    @PATCH("bank_cash_withdraw/{id}")
    Call<EditBankCashWithdrawResponse> editbankcashwithdraw(@Body RequestCreateBankCashWithdraw Payload, @Path("id") String id);

    @POST("purchase_voucher/{id}")
    Call<CreatePurchaseResponce> createpurchase(@Body RequestCreatePurchase payload, @Path("id") String id);

    @POST("incomes/{id}")
    Call<CreateIncomeResponse> createincome(@Body RequestCreateIncome payload, @Path("id") String id);

    @GET("company_incomes/{id}")
    Call<GetIncomeResponse> getincome(@Path("id") String id);

    @DELETE("incomes/{id}")
    Call<DeleteIncomeResponse> deleteincome(@Path("id") String id);

    @GET("incomes/{id}")
    Call<GetIncomeDetailsResponse> getincomedetails(@Path("id") String id);

    @PATCH("incomes/{id}")
    Call<EditIncomeResponse> editincome(@Body RequestCreateIncome payload, @Path("id") String id);

    @POST("expenses/{id}")
    Call<CreateExpenceResponse> createexpence(@Body RequestCreateEXpence payload, @Path("id") String id);

    @GET("company_expenses/{id}")
    Call<GetExpenceResponse> getexpence(@Path("id") String id);

    @DELETE("expenses/{id}")
    Call<DeleteExpenceResponse> deleteexpence(@Path("id") String id);

    @GET("expenses/{id}")
    Call<GetExpenceDetailsResponse> getexpencedetails(@Path("id") String id);

    @PATCH("expenses/{id}")
    Call<EditExpenceResponse> editexpence(@Body RequestCreateEXpence payload, @Path("id") String id);

}

