package com.lkintechnology.mBilling.networks;

import com.lkintechnology.mBilling.networks.api_request.RequestBasic;
import com.lkintechnology.mBilling.networks.api_request.RequestCompanyAdditional;
import com.lkintechnology.mBilling.networks.api_request.RequestCompanyAuthenticate;
import com.lkintechnology.mBilling.networks.api_request.RequestCompanyDetails;
import com.lkintechnology.mBilling.networks.api_request.RequestCompanyGst;
import com.lkintechnology.mBilling.networks.api_request.RequestCompanyLogin;
import com.lkintechnology.mBilling.networks.api_request.RequestCompanyLogo;
import com.lkintechnology.mBilling.networks.api_request.RequestCompanySignature;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateAccount;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateAccountGroup;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateAuthorizationSettings;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateBankCashDeposit;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateBankCashWithdraw;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateBillSundry;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateCompany;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateCreditNote;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateDebitNote;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateDefaultItems;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateEXpence;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateIncome;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateJournalVoucher;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateMaterialCentre;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateMaterialCentreGroup;
import com.lkintechnology.mBilling.networks.api_request.RequestCreatePayment;
import com.lkintechnology.mBilling.networks.api_request.RequestCreatePurchase;
import com.lkintechnology.mBilling.networks.api_request.RequestCreatePurchaseReturn;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateReceipt;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateSaleReturn;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateSaleVoucher;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateStockTransfer;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateUnit;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateUnitConversion;
import com.lkintechnology.mBilling.networks.api_request.RequestEditLogin;
import com.lkintechnology.mBilling.networks.api_request.RequestForgotPassword;
import com.lkintechnology.mBilling.networks.api_request.RequestLoginEmail;
import com.lkintechnology.mBilling.networks.api_request.RequestNewPassword;
import com.lkintechnology.mBilling.networks.api_request.RequestPlan;
import com.lkintechnology.mBilling.networks.api_request.RequestRegister;
import com.lkintechnology.mBilling.networks.api_request.RequestResendOtp;
import com.lkintechnology.mBilling.networks.api_request.RequestUpdateMobileNumber;
import com.lkintechnology.mBilling.networks.api_request.RequestUpdateUser;
import com.lkintechnology.mBilling.networks.api_request.RequestVerification;

import com.lkintechnology.mBilling.networks.api_response.CompanyReportResponse;
import com.lkintechnology.mBilling.networks.api_response.companylogin.CreateAuthorizationSettingsResponse;
import com.lkintechnology.mBilling.networks.api_response.defaultitems.CreateDefaultItemsResponse;
import com.lkintechnology.mBilling.networks.api_response.pdc.GetPdcResponse;
import com.lkintechnology.mBilling.networks.api_response.GetVoucherNumbersResponse;
import com.lkintechnology.mBilling.networks.api_response.companydashboardinfo.GetCompanyDashboardInfoResponse;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.CreatePurchaseReturnResponse;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.DeletePurchaseReturnVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.GetPurchaseReturnVoucherDetails;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.GetPurchaseReturnVoucherListResponse;
import com.lkintechnology.mBilling.networks.api_response.purchasevoucher.DeletePurchaseVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.purchasevoucher.GetPurchaseVoucherDetails;
import com.lkintechnology.mBilling.networks.api_response.purchasevoucher.GetPurchaseVoucherListResponse;
import com.lkintechnology.mBilling.networks.api_response.sale_return.DeleteSaleReturnVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.sale_return.GetSaleReturnVoucherDetails;
import com.lkintechnology.mBilling.networks.api_response.sale_return.GetSaleReturnVoucherListResponse;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.CreateSaleVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashwithdraw.CreateBankCashWithdrawResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashwithdraw.DeleteBankCashWithdrawResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashwithdraw.EditBankCashWithdrawResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashwithdraw.GetBankCashWithdrawDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashwithdraw.GetBankCashWithdrawResponse;
import com.lkintechnology.mBilling.networks.api_response.creditnotewoitem.CreateCreditNoteResponse;
import com.lkintechnology.mBilling.networks.api_response.creditnotewoitem.DeleteCreditNoteResponse;
import com.lkintechnology.mBilling.networks.api_response.creditnotewoitem.EditCreditNoteResponse;
import com.lkintechnology.mBilling.networks.api_response.creditnotewoitem.GetCreditNoteDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.creditnotewoitem.GetCreditNoteResponse;
import com.lkintechnology.mBilling.networks.api_response.debitnotewoitem.CreateDebitNoteResponse;
import com.lkintechnology.mBilling.networks.api_response.debitnotewoitem.DeleteDebitNoteResponse;
import com.lkintechnology.mBilling.networks.api_response.debitnotewoitem.EditDebitNoteResponse;
import com.lkintechnology.mBilling.networks.api_response.debitnotewoitem.GetDebitNoteDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.debitnotewoitem.GetDebitNoteResponse;
import com.lkintechnology.mBilling.networks.api_response.expence.CreateExpenceResponse;
import com.lkintechnology.mBilling.networks.api_response.expence.DeleteExpenceResponse;
import com.lkintechnology.mBilling.networks.api_response.expence.EditExpenceResponse;
import com.lkintechnology.mBilling.networks.api_response.expence.GetExpenceDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.expence.GetExpenceResponse;
import com.lkintechnology.mBilling.networks.api_response.journalvoucher.CreateJournalVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.journalvoucher.DeleteJournalVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.journalvoucher.EditJournalVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.journalvoucher.GetJournalVoucherDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.journalvoucher.GetJournalVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.payment.CreatePaymentResponse;
import com.lkintechnology.mBilling.networks.api_response.payment.DeletePaymentResponse;
import com.lkintechnology.mBilling.networks.api_response.payment.EditPaymentResponse;
import com.lkintechnology.mBilling.networks.api_response.payment.GetPaymentDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.payment.GetPaymentResponse;
import com.lkintechnology.mBilling.networks.api_response.purchase.CreatePurchaseResponce;
import com.lkintechnology.mBilling.networks.api_response.income.DeleteIncomeResponse;
import com.lkintechnology.mBilling.networks.api_response.income.EditIncomeResponse;
import com.lkintechnology.mBilling.networks.api_response.income.GetIncomeDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.income.GetIncomeResponse;
import com.lkintechnology.mBilling.networks.api_response.receiptvoucher.CreateReceiptVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.receiptvoucher.DeleteReceiptVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.receiptvoucher.EditReceiptVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.receiptvoucher.GetReceiptVoucherDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.receiptvoucher.GetReceiptVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.income.CreateIncomeResponse;
import com.lkintechnology.mBilling.networks.api_response.account.CreateAccountResponse;
import com.lkintechnology.mBilling.networks.api_response.account.DeleteAccountResponse;
import com.lkintechnology.mBilling.networks.api_response.account.EditAccountResponse;
import com.lkintechnology.mBilling.networks.api_response.account.GetAccountDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.account.GetAccountResponse;
import com.lkintechnology.mBilling.networks.api_response.accountgroup.CreateAccountGroupResponse;
import com.lkintechnology.mBilling.networks.api_response.accountgroup.DeleteAccountGroupResponse;
import com.lkintechnology.mBilling.networks.api_response.accountgroup.EditAccountGroupResponse;
import com.lkintechnology.mBilling.networks.api_response.accountgroup.GetAccountGroupDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.accountgroup.GetAccountGroupResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashdeposit.CreateBankCashDepositResponse;
import com.lkintechnology.mBilling.networks.api_response.bill_sundry.CreateBillSundryResponse;
import com.lkintechnology.mBilling.networks.api_response.bill_sundry.DeleteBillSundryResponse;
import com.lkintechnology.mBilling.networks.api_response.bill_sundry.EditBillSundryResponse;
import com.lkintechnology.mBilling.networks.api_response.bill_sundry.GetBillSundryDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.bill_sundry.GetBillSundryListResponse;
import com.lkintechnology.mBilling.networks.api_response.bill_sundry.GetBillSundryNatureResponse;
import com.lkintechnology.mBilling.networks.api_response.company.CompanyAuthenticateResponse;
import com.lkintechnology.mBilling.networks.api_response.company.CompanyListResponse;
import com.lkintechnology.mBilling.networks.api_response.company.CreateCompanyResponse;
import com.lkintechnology.mBilling.networks.api_response.company.DeleteCompanyResponse;
import com.lkintechnology.mBilling.networks.api_response.company.IndustryTypeResponse;
import com.lkintechnology.mBilling.networks.api_response.companylogin.CompanyLoginResponse;
import com.lkintechnology.mBilling.networks.api_response.companylogin.CompanyUserResponse;
import com.lkintechnology.mBilling.networks.api_response.getcompany.CompanyResponse;
import com.lkintechnology.mBilling.networks.api_response.item.CreateItemResponse;
import com.lkintechnology.mBilling.networks.api_response.item.DeleteItemResponse;
import com.lkintechnology.mBilling.networks.api_response.item.EditItemResponse;
import com.lkintechnology.mBilling.networks.api_response.item.GetItemDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentre.CreateMaterialCentreResponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentre.DeleteMaterialCentreResponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentre.EditMaterialCentreReponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentre.GetMaterialCentreDetailResponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentre.GetMaterialCentreListResponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentre.StockResponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentregroup.CreateMaterialCentreGroupResponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentregroup.DeleteMaterialCentreGroupResponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentregroup.EditMaterialCentreGroupResponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentregroup.GetMaterialCentreGroupDetailResponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentregroup.GetMaterialCentreGroupListResponse;
import com.lkintechnology.mBilling.networks.api_response.otp.OtpResponse;
import com.lkintechnology.mBilling.networks.api_response.packages.GetPackageResponse;
import com.lkintechnology.mBilling.networks.api_response.packages.PlanResponse;
import com.lkintechnology.mBilling.networks.api_response.purchasetype.GetPurchaseTypeResponse;
import com.lkintechnology.mBilling.networks.api_response.sale_return.CreateSaleReturnResponse;
import com.lkintechnology.mBilling.networks.api_response.saletype.GetSaleTypeResponse;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.DeleteSaleVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.GetSaleVoucherDetails;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.GetSaleVoucherListResponse;
import com.lkintechnology.mBilling.networks.api_response.defaultitems.GetDefaultItemsResponse;
import com.lkintechnology.mBilling.networks.api_response.stocktransfer.CreateStockTransferResponse;
import com.lkintechnology.mBilling.networks.api_response.taxcategory.GetTaxCategoryResponse;
import com.lkintechnology.mBilling.networks.api_response.transactionpdfresponse.GetTransactionPdfResponse;
import com.lkintechnology.mBilling.networks.api_response.unit.GetUqcResponse;
import com.lkintechnology.mBilling.networks.api_response.user.UserApiResponse;
import com.lkintechnology.mBilling.networks.api_response.userexist.UserExistResponse;
import com.lkintechnology.mBilling.networks.api_response.unit.CreateUnitResponse;
import com.lkintechnology.mBilling.networks.api_response.unit.DeleteUnitResponse;
import com.lkintechnology.mBilling.networks.api_response.unit.EditUnitResponse;
import com.lkintechnology.mBilling.networks.api_response.unit.GetUnitDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.unit.GetUnitListResponse;
import com.lkintechnology.mBilling.networks.api_response.unitconversion.CreateUnitConversionResponse;
import com.lkintechnology.mBilling.networks.api_response.unitconversion.DeleteUnitConversionResponse;
import com.lkintechnology.mBilling.networks.api_response.unitconversion.EditUnitConversionResponse;
import com.lkintechnology.mBilling.networks.api_response.unitconversion.GetUnitConversionDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.unitconversion.GetUnitConversionListResponse;
import com.lkintechnology.mBilling.networks.api_response.item.GetItemResponse;
import com.lkintechnology.mBilling.networks.api_response.itemgroup.CreateItemGroupResponse;
import com.lkintechnology.mBilling.networks.api_response.itemgroup.DeleteItemGroupReponse;
import com.lkintechnology.mBilling.networks.api_response.itemgroup.EditItemGroupResponse;
import com.lkintechnology.mBilling.networks.api_response.itemgroup.GetItemGroupDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.itemgroup.GetItemGroupResponse;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateItem;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateItemGroup;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.lkintechnology.mBilling.networks.api_response.bankcashdeposit.DeleteBankCashDepositResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashdeposit.EditBankCashDepositResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashdeposit.GetBankCashDepositDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashdeposit.GetBankCashDepositResponse;
import com.lkintechnology.mBilling.networks.api_response.version.VersionResponse;

public interface Api {
    @POST("signup")
    Call<UserApiResponse> register(@Body RequestRegister payload);

    @PATCH("update_user")
    Call<UserApiResponse> updateuser(@Body RequestUpdateUser payload);

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

    @GET("company_details/{id}")
    Call<CompanyResponse> getcompany(@Path("id") String id);

    @GET("company/{unique_id}")
    Call<CompanyResponse> searchcompany(@Path("unique_id") String unique_id,@Query("phone_number") String phone_number);

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
    Call<GetItemResponse> getitem(@Path("id") String id,@Query("material_center_id") String material_center_id );

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

    @DELETE("sale_voucher/{id}")
    Call<DeleteSaleVoucherResponse> deletesalevoucher(@Path("id") String id);

    @GET("company_bank_cash_deposits/{id}")
    Call<GetBankCashDepositResponse> getbankcashdeposit(@Path("id") String id,@Query("duration") String duration);

    @DELETE("bank_cash_deposits/{id}")
    Call<DeleteBankCashDepositResponse> deletebankcashdeposit(@Path("id") String id);

    @GET("bank_cash_deposits/{id}")
    Call<GetBankCashDepositDetailsResponse> getbankcashdepositdetails(@Path("id") String id);

    @PATCH("bank_cash_deposits/{id}")
    Call<EditBankCashDepositResponse> editbankcashdeposit(@Body RequestCreateBankCashDeposit payload, @Path("id") String id);

    @POST("bank_cash_deposits/{id}")
    Call<CreateBankCashDepositResponse> createbankcashdeposit(@Body RequestCreateBankCashDeposit payload, @Path("id") String id);

    @POST("bank_cash_withdraw/{id}")
    Call<CreateBankCashWithdrawResponse> createbankcashwithdraw(@Body RequestCreateBankCashWithdraw payload, @Path("id") String id);

    @GET("company_bank_cash_withdraw/{id}")
    Call<GetBankCashWithdrawResponse> getbankcashwithdraw(@Path("id") String id,@Query("duration") String duration);

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
    Call<GetIncomeResponse> getincome(@Path("id") String id,@Query("duration") String duration);

    @DELETE("incomes/{id}")
    Call<DeleteIncomeResponse> deleteincome(@Path("id") String id);

    @GET("incomes/{id}")
    Call<GetIncomeDetailsResponse> getincomedetails(@Path("id") String id);

    @PATCH("incomes/{id}")
    Call<EditIncomeResponse> editincome(@Body RequestCreateIncome payload, @Path("id") String id);

    @POST("expenses/{id}")
    Call<CreateExpenceResponse> createexpence(@Body RequestCreateEXpence payload, @Path("id") String id);

    @GET("company_expenses/{id}")
    Call<GetExpenceResponse> getexpence(@Path("id") String id,@Query("duration") String duration);

    @DELETE("expenses/{id}")
    Call<DeleteExpenceResponse> deleteexpence(@Path("id") String id);

    @GET("expenses/{id}")
    Call<GetExpenceDetailsResponse> getexpencedetails(@Path("id") String id);

    @PATCH("expenses/{id}")
    Call<EditExpenceResponse> editexpence(@Body RequestCreateEXpence payload, @Path("id") String id);

    @POST("payments/{id}")
    Call<CreatePaymentResponse> createpayment(@Body RequestCreatePayment payload, @Path("id") String id);

    @GET("company_payments/{id}")
    Call<GetPaymentResponse> getpayment(@Path("id") String id,@Query("duration") String duration);

    @DELETE("payments/{id}")
    Call<DeletePaymentResponse> deletepayment(@Path("id") String id);

    @GET("payments/{id}")
    Call<GetPaymentDetailsResponse> getpaymentdetails(@Path("id") String id);

    @PATCH("payments/{id}")
    Call<EditPaymentResponse> editpayment(@Body RequestCreatePayment payload, @Path("id") String id);

    @POST("journal_voucher/{id}")
    Call<CreateJournalVoucherResponse> createjournalvoucher(@Body RequestCreateJournalVoucher payload, @Path("id") String id);

    @GET("company_journal_voucher/{id}")
    Call<GetJournalVoucherResponse> getjournalvoucher(@Path("id") String id,@Query("duration") String duration);

    @DELETE("journal_voucher/{id}")
    Call<DeleteJournalVoucherResponse> deletejournalvoucher(@Path("id") String id);

    @GET("journal_voucher/{id}")
    Call<GetJournalVoucherDetailsResponse> getjournalvoucherdetails(@Path("id") String id);

    @PATCH("journal_voucher/{id}")
    Call<EditJournalVoucherResponse> editjournalvoucher(@Body RequestCreateJournalVoucher payload, @Path("id") String id);

    @POST("receipt_vouchers/{id}")
    Call<CreateReceiptVoucherResponse> createreceipt(@Body RequestCreateReceipt payload, @Path("id") String id);

    @GET("company_receipt_vouchers/{id}")
    Call<GetReceiptVoucherResponse> getreceiptvoucher(@Path("id") String id,@Query("duration") String duration);

    @DELETE("receipt_vouchers/{id}")
    Call<DeleteReceiptVoucherResponse> deletereceiptvoucher(@Path("id") String id);

    @GET("receipt_vouchers/{id}")
    Call<GetReceiptVoucherDetailsResponse> getreceiptvoucherdetails(@Path("id") String id);

    @PATCH("receipt_vouchers/{id}")
    Call<EditReceiptVoucherResponse> editreceiptvoucher(@Body RequestCreateReceipt payload, @Path("id") String id);

    @POST("credit_notes/{id}")
    Call<CreateCreditNoteResponse> createcreditnote(@Body RequestCreateCreditNote payload, @Path("id") String id);

    @GET("company_credit_notes/{id}")
    Call<GetCreditNoteResponse> getcreditnote(@Path("id") String id,@Query("duration") String duration);

    @DELETE("credit_notes/{id}")
    Call<DeleteCreditNoteResponse> deletecrdeitnote(@Path("id") String id);

    @GET("credit_notes/{id}")
    Call<GetCreditNoteDetailsResponse> getcreditnotedetails(@Path("id") String id);

    @PATCH("credit_notes/{id}")
    Call<EditCreditNoteResponse> editcreditnote(@Body RequestCreateCreditNote payload, @Path("id") String id);

    @POST("debit_notes/{id}")
    Call<CreateDebitNoteResponse> createdebitnote(@Body RequestCreateDebitNote payload, @Path("id") String id);

    @GET("company_debit_notes/{id}")
    Call<GetDebitNoteResponse> getdebitnote(@Path("id") String id,@Query("duration") String duration);

    @DELETE("debit_notes/{id}")
    Call<DeleteDebitNoteResponse> deletedebitnote(@Path("id") String id);

    @GET("debit_notes/{id}")
    Call<GetDebitNoteDetailsResponse> getdebitnotedetails(@Path("id") String id);

    @PATCH("debit_notes/{id}")
    Call<EditDebitNoteResponse> editdebitnote(@Body RequestCreateDebitNote payload, @Path("id") String id);

    @POST("sale_return_voucher/{id}")
    Call<CreateSaleReturnResponse> createSaleReturn(@Body RequestCreateSaleReturn createItem, @Path("id") String id);

    @POST("purchase_return_voucher/{id}")
    Call<CreatePurchaseReturnResponse> createPurchaseReturn(@Body RequestCreatePurchaseReturn createItem, @Path("id") String id);

    @GET("company/dashboard/{id}")
    Call<GetCompanyDashboardInfoResponse> getcompanydashboardinfo(@Path("id") String id);

    @GET("company/company_data_report/{id}")
    Call<GetTransactionPdfResponse> gettransactionpdf(@Path("id") String id, @Query("account_id") String account_id, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("voucher_series/{id}")
    Call<GetVoucherNumbersResponse> getvouchernumbers(@Path("id") String id, @Query("voucher_type") String voucher_type);

    @GET("company_sale_vouchers/{id}")
    Call<GetSaleVoucherListResponse> getsalevoucher(@Path("id") String id, @Query("duration") String duration);

    @GET("company_purchase_vouchers/{id}")
    Call<GetPurchaseVoucherListResponse> getpurchasevoucher(@Path("id") String id, @Query("duration") String duration);

    @GET("pdc_details/{id}")
    Call<GetPdcResponse> getPdc(@Path("id") String id, @Query("duration") String duration);

    @GET("company_sale_return_vouchers/{id}")
    Call<GetSaleReturnVoucherListResponse> getsalereturnvoucher(@Path("id") String id, @Query("duration") String duration);

    @DELETE("sale_return_voucher/{id}")
    Call<DeleteSaleReturnVoucherResponse> deletesalereturnvoucher(@Path("id") String id);

    @GET("company_purchase_return_vouchers/{id}")
    Call<GetPurchaseReturnVoucherListResponse>getpurchasereturnvoucher(@Path("id") String id, @Query("duration") String duration);

    @DELETE("purchase_voucher/{id}")
    Call<DeletePurchaseVoucherResponse> deletepurchasevoucher(@Path("id") String id);

    @DELETE("purchase_return_voucher/{id}")
    Call<DeletePurchaseReturnVoucherResponse> deletepurchasereturnvoucher(@Path("id") String id);

    @GET("version")
    Call<VersionResponse> version(@Query("device_type") String device_type);


    @GET("default_items")
    Call<GetDefaultItemsResponse> getDefaultItems(@Query("company_id") String company_id);


    @POST("create_default_items")
    Call<CreateDefaultItemsResponse> createDefaultItems(@Body RequestCreateDefaultItems payload);

    @GET("sale_voucher/{id}")
    Call<GetSaleVoucherDetails> getSaleVoucherDetails(@Path("id") String id);

    @GET("purchase_vouchers/{id}")
    Call<GetPurchaseVoucherDetails> getPurchaseVoucherDetails(@Path("id") String id);

    @GET("purchase_return_voucher/{id}")
    Call<GetPurchaseReturnVoucherDetails> getPurchaseReturnVoucherDetails(@Path("id") String id);

    @GET("sale_return_voucher/{id}")
    Call<GetSaleReturnVoucherDetails> getSaleReturnVoucherDetails(@Path("id") String id);

    @PATCH("sale_voucher/{id}")
    Call<CreateSaleVoucherResponse> updateSaleVoucherDetails(@Body RequestCreateSaleVoucher payload,@Path("id") String id);

    @PATCH("purchase_vouchers/{id}")
    Call<CreatePurchaseResponce> updatePurchaseVoucherDetails(@Body RequestCreatePurchase payload,@Path("id") String id);

    @PATCH("purchase_return_voucher/{id}")
    Call<CreatePurchaseReturnResponse> updatePurchaseReturnVoucherDetails(@Body RequestCreatePurchaseReturn payload,@Path("id") String id);

    @PATCH("sale_return_voucher/{id}")
    Call<CreateSaleReturnResponse> updateSaleReturnVoucherDetails(@Body RequestCreateSaleReturn payload,@Path("id") String id);

    @GET("company/gst_report/sale/{id}")
    Call<CompanyReportResponse> getcompanyreport(@Path("id") String id, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("company/gst_report/purchase/{id}")
    Call<CompanyReportResponse> getgstreportpurchase(@Path("id") String id, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("company/gst_report/sale/{id}")
    Call<CompanyReportResponse> getprofitandloss(@Path("id") String id, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @POST("stock_transfer_vouchers/{id}")
    Call<CreateStockTransferResponse> createStockTransfer(@Body RequestCreateStockTransfer payload,@Path("id") String id);

    @POST("authorization_settings")
    Call<CreateAuthorizationSettingsResponse> createAuthorizationSettings(@Body RequestCreateAuthorizationSettings payload/*@Path("id") String id*/);

}