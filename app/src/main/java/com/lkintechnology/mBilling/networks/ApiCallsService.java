package com.lkintechnology.mBilling.networks;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.lkintechnology.mBilling.ThisApp;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.api_request.RequestBasic;
import com.lkintechnology.mBilling.networks.api_request.RequestCheckBarcode;
import com.lkintechnology.mBilling.networks.api_request.RequestCompanyAdditional;
import com.lkintechnology.mBilling.networks.api_request.RequestCompanyAuthenticate;
import com.lkintechnology.mBilling.networks.api_request.RequestCompanyBankDetails;
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
import com.lkintechnology.mBilling.networks.api_request.RequestCreatePOSVoucher;
import com.lkintechnology.mBilling.networks.api_request.RequestCreatePayment;
import com.lkintechnology.mBilling.networks.api_request.RequestCreatePurchase;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateReceipt;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateSaleReturn;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateSaleVoucher;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateStockTransfer;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateUnit;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateUnitConversion;
import com.lkintechnology.mBilling.networks.api_request.RequestEditLogin;
import com.lkintechnology.mBilling.networks.api_request.RequestForgotPassword;
import com.lkintechnology.mBilling.networks.api_request.RequestInvoiceFormat;
import com.lkintechnology.mBilling.networks.api_request.RequestLoginEmail;
import com.lkintechnology.mBilling.networks.api_request.RequestNewPassword;
import com.lkintechnology.mBilling.networks.api_request.RequestPlan;
import com.lkintechnology.mBilling.networks.api_request.RequestRegister;
import com.lkintechnology.mBilling.networks.api_request.RequestResendOtp;
import com.lkintechnology.mBilling.networks.api_request.RequestUpdateMobileNumber;
import com.lkintechnology.mBilling.networks.api_request.RequestUpdateUser;
import com.lkintechnology.mBilling.networks.api_request.RequestVerification;
import com.lkintechnology.mBilling.networks.api_response.CompanyReportResponse;
import com.lkintechnology.mBilling.networks.api_response.checkbarcode.CheckBarcodeResponse;
import com.lkintechnology.mBilling.networks.api_response.companylogin.CreateAuthorizationSettingsResponse;
import com.lkintechnology.mBilling.networks.api_response.defaultitems.CreateDefaultItemsResponse;
import com.lkintechnology.mBilling.networks.api_response.defaultitems.GetDefaultItemsResponse;
import com.lkintechnology.mBilling.networks.api_response.item_wise_report.ItemWiseReportResponse;
import com.lkintechnology.mBilling.networks.api_response.pdc.GetPdcResponse;
import com.lkintechnology.mBilling.networks.api_response.GetVoucherNumbersResponse;
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
import com.lkintechnology.mBilling.networks.api_response.bankcashdeposit.DeleteBankCashDepositResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashdeposit.EditBankCashDepositResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashdeposit.GetBankCashDepositDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashdeposit.GetBankCashDepositResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashwithdraw.CreateBankCashWithdrawResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashwithdraw.DeleteBankCashWithdrawResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashwithdraw.EditBankCashWithdrawResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashwithdraw.GetBankCashWithdrawDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.bankcashwithdraw.GetBankCashWithdrawResponse;
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
import com.lkintechnology.mBilling.networks.api_response.companydashboardinfo.GetCompanyDashboardInfoResponse;
import com.lkintechnology.mBilling.networks.api_response.companylogin.CompanyLoginResponse;
import com.lkintechnology.mBilling.networks.api_response.companylogin.CompanyUserResponse;
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
import com.lkintechnology.mBilling.networks.api_response.getcompany.CompanyResponse;
import com.lkintechnology.mBilling.networks.api_response.income.CreateIncomeResponse;
import com.lkintechnology.mBilling.networks.api_response.income.DeleteIncomeResponse;
import com.lkintechnology.mBilling.networks.api_response.income.EditIncomeResponse;
import com.lkintechnology.mBilling.networks.api_response.income.GetIncomeDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.income.GetIncomeResponse;
import com.lkintechnology.mBilling.networks.api_response.item.DeleteItemResponse;
import com.lkintechnology.mBilling.networks.api_response.item.EditItemResponse;
import com.lkintechnology.mBilling.networks.api_response.item.GetItemDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.itemgroup.CreateItemGroupResponse;
import com.lkintechnology.mBilling.networks.api_response.journalvoucher.CreateJournalVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.journalvoucher.DeleteJournalVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.journalvoucher.EditJournalVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.journalvoucher.GetJournalVoucherDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.journalvoucher.GetJournalVoucherResponse;
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
import com.lkintechnology.mBilling.networks.api_response.payment.CreatePaymentResponse;
import com.lkintechnology.mBilling.networks.api_response.payment.DeletePaymentResponse;
import com.lkintechnology.mBilling.networks.api_response.payment.EditPaymentResponse;
import com.lkintechnology.mBilling.networks.api_response.payment.GetPaymentDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.payment.GetPaymentResponse;
import com.lkintechnology.mBilling.networks.api_response.pdf.PdfResponse;
import com.lkintechnology.mBilling.networks.api_response.purchase.CreatePurchaseResponce;
import com.lkintechnology.mBilling.networks.api_response.purchase.UpdatePurchaseResponse;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.CreatePurchaseReturnResponse;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.DeletePurchaseReturnVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.GetPurchaseReturnVoucherDetails;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.GetPurchaseReturnVoucherListResponse;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.UpdatePurchaseReturnVoucher;
import com.lkintechnology.mBilling.networks.api_response.purchasetype.GetPurchaseTypeResponse;
import com.lkintechnology.mBilling.networks.api_response.purchasevoucher.DeletePurchaseVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.purchasevoucher.GetPurchaseVoucherDetails;
import com.lkintechnology.mBilling.networks.api_response.purchasevoucher.GetPurchaseVoucherListResponse;
import com.lkintechnology.mBilling.networks.api_response.receiptvoucher.CreateReceiptVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.receiptvoucher.DeleteReceiptVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.receiptvoucher.EditReceiptVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.receiptvoucher.GetReceiptVoucherDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.receiptvoucher.GetReceiptVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.sale_return.DeleteSaleReturnVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.sale_return.GetSaleReturnVoucherDetails;
import com.lkintechnology.mBilling.networks.api_response.sale_return.GetSaleReturnVoucherListResponse;
import com.lkintechnology.mBilling.networks.api_response.sale_return.UpdateSaleReturnResponse;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.CreateSaleVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.sale_return.CreateSaleReturnResponse;
import com.lkintechnology.mBilling.networks.api_response.saletype.GetSaleTypeResponse;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.DeleteSaleVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.GetSaleVoucherDetails;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.GetSaleVoucherListResponse;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.UpdateSaleVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.salevouchersitem.GetSaleVouchersItem;
import com.lkintechnology.mBilling.networks.api_response.serialnumber.SerialNumberReferenceResponse;
import com.lkintechnology.mBilling.networks.api_response.stocktransfer.CreateStockTransferResponse;
import com.lkintechnology.mBilling.networks.api_response.stocktransfer.DeleteStockTransferResponse;
import com.lkintechnology.mBilling.networks.api_response.stocktransfer.GetStockTransferDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.stocktransfer.GetStockTransferListResponse;
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
import com.lkintechnology.mBilling.networks.api_request.RequestCreateItem;
import com.lkintechnology.mBilling.networks.api_request.RequestCreateItemGroup;
import com.lkintechnology.mBilling.networks.api_response.itemgroup.DeleteItemGroupReponse;
import com.lkintechnology.mBilling.networks.api_response.itemgroup.EditItemGroupResponse;
import com.lkintechnology.mBilling.networks.api_response.itemgroup.GetItemGroupDetailsResponse;
import com.lkintechnology.mBilling.networks.api_response.itemgroup.GetItemGroupResponse;
import com.lkintechnology.mBilling.networks.api_response.item.CreateItemResponse;
import com.lkintechnology.mBilling.networks.api_response.item.GetItemResponse;
import com.lkintechnology.mBilling.networks.api_response.version.VersionResponse;
import com.lkintechnology.mBilling.networks.api_response.voucherseries.VoucherSeriesResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.networks.api_request.RequestCreatePurchaseReturn;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by ADMIN on 3/6/2017.
 */
public class ApiCallsService extends IntentService {
    private Api api;

    public ApiCallsService() {
        super(Cv.SERVICE_NAME);
    }

    public static void action(Context ctx, String action) {
        Intent intent = new Intent(ctx, ApiCallsService.class);
        intent.setAction(action);
        ctx.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        api = ThisApp.getApi(this);
        if (Cv.ACTION_REGISTER_USER.equals(action)) {
            handleRegister();
        } else if (Cv.ACTION_UPDATE_USER.equals(action)) {
            handleUpdateUser();
        } else if (Cv.ACTION_FORGOT_PASSWORD.equals(action)) {
            handleForgotPassword();
        } else if (Cv.ACTION_VERIFICATION.equals(action)) {
            handleVerifyOtp();
        } else if (Cv.ACTION_NEW_PASSWORD.equals(action)) {
            handleNewPassword();
        } else if (Cv.ACTION_UPDATE_MOBILE_NUMBER.equals(action)) {
            handleUpdateMobileNumber();
        } else if (Cv.ACTION_RESEND_OTP.equals(action)) {
            handleResendOtp();
        } else if (Cv.ACTION_LOGIN.equals(action)) {
            handleLogin();
        } else if (Cv.ACTION_FACEBOOK_CHECK.equals(action)) {
            handlefacebookcheck();
        } else if (Cv.ACTION_CREATE_COMPANY.equals(action)) {
            handleCreateCompany();
        } else if (Cv.ACTION_CREATE_BASIC.equals(action)) {
            handleBasic();
        } else if (Cv.ACTION_CREATE_DETAILS.equals(action)) {
            handleCompanyDetails();
        } else if (Cv.ACTION_CREATE_BANK_DETAILS.equals(action)) {
            handleCompanyBankDetails();
        } else if (Cv.ACTION_CREATE_GST.equals(action)) {
            handleCompanyGst();
        } else if (Cv.ACTION_CREATE_ADDITIONAL.equals(action)) {
            handleCompanyAdditional();
        } else if (Cv.ACTION_CREATE_LOGO.equals(action)) {
            handleCompanyLogo();
        } else if (Cv.ACTION_CREATE_SIGNATURE.equals(action)) {
            handleCompanySignature();
        } else if (Cv.ACTION_CREATE_LOGIN.equals(action)) {
            handleCompanyLogin();
        } else if (Cv.ACTION_COMPANY_LIST.equals(action)) {
            handleCompanyList();
        } else if (Cv.ACTION_DELETE_COMPANY.equals(action)) {
            handleDeleteCompany();
        } else if (Cv.ACTION_GET_INDUSTRY.equals(action)) {
            handleGetIndustry();
        } else if (Cv.ACTION_COMPANY_AUTHENTICATE.equals(action)) {
            handleAuthenticateCompany();
        } else if (Cv.ACTION_GET_COMPANY.equals(action)) {
            handleGetCompany();
        } else if (Cv.ACTION_SEARCH_COMPANY.equals(action)) {
            handleSearchCompany();
        } else if (Cv.ACTION_GET_COMPANY_USER.equals(action)) {
            handleGetCompanyUser();
        } else if (Cv.ACTION_EDIT_LOGIN.equals(action)) {
            handleEditLogin();
        } else if (Cv.ACTION_CREATE_ACCOUNT_GROUP.equals(action)) {
            handleCreateAccountGroup();
        } else if (Cv.ACTION_GET_ACCOUNT_GROUP.equals(action)) {
            handleGetAccountGroup();
        } else if (Cv.ACTION_GET_ACCOUNT_GROUP_DETAILS.equals(action)) {
            handleGetAccountGroupDetails();
        } else if (Cv.ACTION_EDIT_ACCOUNT_GROUP.equals(action)) {
            handleEditAccountGroup();
        } else if (Cv.ACTION_CREATE_ACCOUNT.equals(action)) {
            handleCreateAccount();
        } else if (Cv.ACTION_GET_ACCOUNT.equals(action)) {
            handleGetAccount();
        } else if (Cv.ACTION_GET_ACCOUNT_DETAILS.equals(action)) {
            handleGetAccountDetails();
        } else if (Cv.ACTION_EDIT_ACCOUNT.equals(action)) {
            handleEditAccount();
        } else if (Cv.ACTION_DELETE_ACCOUNT.equals(action)) {
            handleDeleteAccount();
        } else if (Cv.ACTION_DELETE_ACCOUNT_GROUP.equals(action)) {
            handleDeleteAccountGroup();
        } else if (Cv.ACTION_GET_PACKAGES.equals(action)) {
            handleGetPackages();
        } else if (Cv.ACTION_PLANS.equals(action)) {
            handlePlans();
        } else if (Cv.ACTION_GET_MATERIAL_CENTRE_GROUP_LIST.equals(action)) {
            handleGetMaterialCentreGroupList();
        } else if (Cv.ACTION_CREATE_MATERIAL_CENTRE_GROUP.equals(action)) {
            handleCreateMaterialCentreGroup();
        } else if (Cv.ACTION_EDIT_MATERIAL_CENTRE_GROUP.equals(action)) {
            handleEditMaterialCentreGroup();
        } else if (Cv.ACTION_DELETE_MATERIAL_CENTRE_GROUP.equals(action)) {
            handleDeleteMaterialCentreGroup();
        } else if (Cv.ACTION_GET_MATERIAL_CENTRE_GROUP_DETAILS.equals(action)) {
            handleGetMaterialCentreGroupDetails();
        } else if (Cv.ACTION_GET_MATERIAL_CENTRE_LIST.equals(action)) {
            handleGetMaterialCentreList();
        } else if (Cv.ACTION_CREATE_MATERIAL_CENTRE.equals(action)) {
            handleCreateMaterialCentre();
        } else if (Cv.ACTION_EDIT_MATERIAL_CENTRE.equals(action)) {
            handleEditMaterialCentre();
        } else if (Cv.ACTION_DELETE_MATERIAL_CENTRE.equals(action)) {
            handleDeleteMaterialCentre();
        } else if (Cv.ACTION_GET_MATERIAL_CENTRE_DETAILS.equals(action)) {
            handleGetMaterialCentreDetails();
        } else if (Cv.ACTION_GET_STOCK.equals(action)) {
            handleGetStock();
        } else if (Cv.ACTION_GET_UQC.equals(action)) {
            handleGetUqc();
        } else if (Cv.ACTION_GET_UNIT_LIST.equals(action)) {
            handleGetUnitList();
        } else if (Cv.ACTION_CREATE_UNIT.equals(action)) {
            handleCreateUnit();
        } else if (Cv.ACTION_EDIT_UNIT.equals(action)) {
            handleEditUnit();
        } else if (Cv.ACTION_DELETE_UNIT.equals(action)) {
            handleDeleteUnit();
        } else if (Cv.ACTION_GET_UNIT_DETAILS.equals(action)) {
            handleGetUnitDetails();
        } else if (Cv.ACTION_GET_UNIT_CONVERSION_LIST.equals(action)) {
            handleGetUnitConversionList();
        } else if (Cv.ACTION_CREATE_UNIT_CONVERSION.equals(action)) {
            handleCreateUnitConversion();
        } else if (Cv.ACTION_EDIT_UNIT_CONVERSION.equals(action)) {
            handleEditUnitConversion();
        } else if (Cv.ACTION_DELETE_UNIT_CONVERSION.equals(action)) {
            handleDeleteUnitConversion();
        } else if (Cv.ACTION_GET_UNIT_CONVERSION_DETAILS.equals(action)) {
            handleGetUnitConversionDetails();
        } else if (Cv.ACTION_GET_ITEM_MATERRIAL_CENTRE.equals(action)) {
            handleGetItemmaterrialCentre();
        } else if (Cv.ACTION_GET_ITEM.equals(action)) {
            handleGetItem();
        } else if (Cv.ACTION_CREATE_ITEM.equals(action)) {
            handleCreateItem();
        } else if (Cv.ACTION_DELETE_ITEM.equals(action)) {
            handleDeleteItem();
        } else if (Cv.ACTION_EDIT_ITEM.equals(action)) {
            handleEditItem();
        } else if (Cv.ACTION_GET_ITEM_DETAILS.equals(action)) {
            handleGetItemDetails();
        } else if (Cv.ACTION_GET_ITEM_GROUP.equals(action)) {
            handleGetItemGroup();
        } else if (Cv.ACTION_CREATE_ITEM_GROUP.equals(action)) {
            handleCreateItemGroup();
        } else if (Cv.ACTION_DELETE_ITEM_GROUP.equals(action)) {
            handleDeleteItemGroup();
        } else if (Cv.ACTION_GET_ITEM_GROUP_DETAILS.equals(action)) {
            handleGetItemGroupDetails();
        } else if (Cv.ACTION_EDIT_ITEM_GROUP.equals(action)) {
            handleEditItemGroup();
        } else if (Cv.ACTION_GET_BILL_SUNDRY_LIST.equals(action)) {
            handleGetBillSundryList();
        } else if (Cv.ACTION_CREATE_BILL_SUNDRY.equals(action)) {
            handleCreateBillSundry();
        } else if (Cv.ACTION_DELETE_BILL_SUNDRY.equals(action)) {
            handleDeleteBillSundry();
        } else if (Cv.ACTION_GET_BILL_SUNDRY_DETAILS.equals(action)) {
            handleGetBillSundryDetails();
        } else if (Cv.ACTION_EDIT_BILL_SUNDRY.equals(action)) {
            handleEditBillSundry();
        } else if (Cv.ACTION_GET_BILL_SUNDRY_NATURE.equals(action)) {
            handleGetBillSundryNature();
        } else if (Cv.ACTION_GET_PURCHASE_TYPE.equals(action)) {
            handleGetPurchaseType();
        } else if (Cv.ACTION_GET_SALE_TYPE.equals(action)) {
            handleGetSaleType();
        } else if (Cv.ACTION_GET_TAX_CATEGORY.equals(action)) {
            hadleGetTaxCategory();
        } else if (Cv.ACTION_CREATE_SALE_VOUCHER.equals(action)) {
            handleSaleVoucher();
        } else if (Cv.ACTION_GET_SALE_VOUCHER_LIST.equals(action)) {
            handleGetSaleVoucherList();
        } else if (Cv.ACTION_DELETE_SALE_VOUCHER.equals(action)) {
            handleDeleteSaleVoucher();
        } else if (Cv.ACTION_GET_BANK_CASH_DEPOSIT.equals(action)) {
            handleGetBankCashDeposit();
        } else if (Cv.ACTION_DELETE_BANK_CASH_DEPOSIT.equals(action)) {
            handleDeleteBankCashDeposit();
        } else if (Cv.ACTION_GET_BANK_CASH_DEPOSIT_DETAILS.equals(action)) {
            handleGetBankCashDepositDetails();
        } else if (Cv.ACTION_EDIT_BANK_CASH_DEPOSIT.equals(action)) {
            handleEditBankCashDeposit();
        } else if (Cv.ACTION_CREATE_BANK_CASH_DEPOSIT.equals(action)) {
            handleCreateBankCashDeposit();
        } else if (Cv.ACTION_CREATE_PURCHASE.equals(action)) {
            handlePurchase();
        } else if (Cv.ACTION_CREATE_BANK_CASH_WITHDRAW.equals(action)) {
            handleCreateBankCashWithdraw();
        } else if (Cv.ACTION_GET_BANK_CASH_WITHDRAW.equals(action)) {
            handleGetBankCashWithdraw();
        } else if (Cv.ACTION_DELETE_BANK_CASH_WITHDRAW.equals(action)) {
            handleDeleteBankCashWithdraw();
        } else if (Cv.ACTION_GET_BANK_CASH_WITHDRAW_DETAILS.equals(action)) {
            handleGetBankCashWithdrawDetails();
        } else if (Cv.ACTION_EDIT_BANK_CASH_WITHDRAW.equals(action)) {
            handleEditBankCashWithdraw();
        } else if (Cv.ACTION_CREATE_INCOME.equals(action)) {
            handleCreateIncome();
        } else if (Cv.ACTION_GET_INCOME.equals(action)) {
            handleGetIncome();
        } else if (Cv.ACTION_DELETE_INCOME.equals(action)) {
            handleDeleteIncome();
        } else if (Cv.ACTION_GET_INCOME_DETAILS.equals(action)) {
            handleGetIncomeDetails();
        } else if (Cv.ACTION_EDIT_INCOME.equals(action)) {
            handleEditIncome();
        } else if (Cv.ACTION_CREATE_EXPENCE.equals(action)) {
            handleCreateExpence();
        } else if (Cv.ACTION_GET_EXPENCE.equals(action)) {
            handleGetExpence();
        } else if (Cv.ACTION_DELETE_EXPENCE.equals(action)) {
            handleDeleteExpence();
        } else if (Cv.ACTION_GET_EXPENCE_DETAILS.equals(action)) {
            handleGetExpenceDetails();
        } else if (Cv.ACTION_EDIT_EXPENCE.equals(action)) {
            handleEditExpence();
        } else if (Cv.ACTION_CREATE_PAYMENT.equals(action)) {
            handleCreatePayment();
        } else if (Cv.ACTION_GET_PAYMENT.equals(action)) {
            handleGetPayment();
        } else if (Cv.ACTION_DELETE_PAYMENT.equals(action)) {
            handleDeletePayment();
        } else if (Cv.ACTION_GET_PAYMENT_DETAILS.equals(action)) {
            handleGetPaymentDetails();
        } else if (Cv.ACTION_EDIT_PAYMENT.equals(action)) {
            handleEditPayment();
        } else if (Cv.ACTION_CREATE_JOURNAL_VOUCHER.equals(action)) {
            handleCreateJournalVoucher();
        } else if (Cv.ACTION_GET_JOURNAL_VOUCHER.equals(action)) {
            handleGetJournalVoucher();
        } else if (Cv.ACTION_DELETE_JOURNAL_VOUCHER.equals(action)) {
            handleDeleteJournalVoucher();
        } else if (Cv.ACTION_GET_JOURNAL_VOUCHER_DETAILS.equals(action)) {
            handleGetJournalVoucherDetails();
        } else if (Cv.ACTION_EDIT_JOURNAL_VOUCHER.equals(action)) {
            handleEditJournalVoucher();
        } else if (Cv.ACTION_CREATE_RECEIPT_VOUCHER.equals(action)) {
            handleCreateReceipt();
        } else if (Cv.ACTION_GET_RECEIPT_VOUCHER.equals(action)) {
            handleGetreceiptVoucher();
        } else if (Cv.ACTION_DELETE_RECEIPT_VOUCHER.equals(action)) {
            handleDeleteReceiptVoucher();
        } else if (Cv.ACTION_GET_RECEIPT_VOUCHER_DETAILS.equals(action)) {
            handleGetReceiptVoucherDetails();
        } else if (Cv.ACTION_EDIT_RECEIPT_VOUCHER.equals(action)) {
            handleEditReceiptVoucher();
        } else if (Cv.ACTION_CREATE_CREDIT_NOTE.equals(action)) {
            handleCreateCreditNote();
        } else if (Cv.ACTION_GET_CREDIT_NOTE.equals(action)) {
            handleGetCreditNote();
        } else if (Cv.ACTION_DELETE_CREDIT_NOTE.equals(action)) {
            handleDeleteCreditNote();
        } else if (Cv.ACTION_GET_CREDIT_NOTE_DETAILS.equals(action)) {
            handleGetCreditNoteDetails();
        } else if (Cv.ACTION_EDIT_CREDIT_NOTE.equals(action)) {
            handleEditCreditNote();
        } else if (Cv.ACTION_CREATE_DEBIT_NOTE.equals(action)) {
            handleCreateDebitNote();
        } else if (Cv.ACTION_GET_DEBIT_NOTE.equals(action)) {
            handleGetDebitNote();
        } else if (Cv.ACTION_DELETE_DEBIT_NOTE.equals(action)) {
            handleDeleteDebitNote();
        } else if (Cv.ACTION_GET_DEBIT_NOTE_DETAILS.equals(action)) {
            handleGetDebitNoteDetails();
        } else if (Cv.ACTION_EDIT_DEBIT_NOTE.equals(action)) {
            handleEditDebitNote();
        } else if (Cv.ACTION_CREATE_SALE_RETURN.equals(action)) {
            handleSaleReturn();
        } else if (Cv.ACTION_CREATE_PURCHASE_RETURN.equals(action)) {
            handlePurchaseReturn();
        } else if (Cv.ACTION_GET_COMPANY_DASHBOARD_INFO.equals(action)) {
            handleGetCompanyDashboardInfo();
        } else if (Cv.ACTION_GET_TRANSACTION_PDF.equals(action)) {
            handleGetTransactionPdf();
        } else if (Cv.ACTION_GET_VOUCHER_NUMBERS.equals(action)) {
            handleGetVoucherNumbers();
        } else if (Cv.ACTION_GET_SALE_VOUCHER.equals(action)) {
            handleGetSaleVoucher();
        } else if (Cv.ACTION_GET_PURCHASE_VOUCHER.equals(action)) {
            handleGetPurchaseVoucher();
        } else if (Cv.ACTION_GET_PDC.equals(action)) {
            handleGetPdc();
        } else if (Cv.ACTION_GET_SALE_RETURN_VOUCHER.equals(action)) {
            handleGetSaleReturnVoucher();
        } else if (Cv.ACTION_GET_PURCHASE_RETURN_VOUCHER.equals(action)) {
            handleGetPurchaseReturnvoucher();
        } else if (Cv.ACTION_DELETE_SALE_RETURN_VOUCHER.equals(action)) {
            handleDeleteSaleReturnVoucher();
        } else if (Cv.ACTION_DELETE_PURCHASE_VOUCHER.equals(action)) {
            handleDeletePurchaseVoucher();
        } else if (Cv.ACTION_DELETE_PURCHASE_RETURN_VOUCHER.equals(action)) {
            handleDeletePurchaseReturnVoucher();
        } else if (Cv.ACTION_VERSION.equals(action)) {
            handleVersion();
        } else if (Cv.ACTION_GET_DEFAULT_ITEMS.equals(action)) {
            handleGetDefaultItems();
        } else if (Cv.ACTION_CREATE_DEFAULT_ITEMS.equals(action)) {
            handleCreateDefaultItems();
        } else if (Cv.ACTION_GET_SALE_VOUCHER_DETAILS.equals(action)) {
            handleGetSaleVoucherDetails();
        } else if (Cv.ACTION_GET_SALE_RETURN_VOUCHER_DETAILS.equals(action)) {
            handleGetSaleReturnVoucherDetails();
        } else if (Cv.ACTION_GET_PURCHASE_VOUCHER_DETAILS.equals(action)) {
            handleGetPurchaseVoucherDetails();
        } else if (Cv.ACTION_GET_PURCHASE_RETURN_VOUCHER_DETAILS.equals(action)) {
            handleGetPurchaseReturnVoucherDetails();
        } else if (Cv.ACTION_GET_GST_REPORT.equals(action)) {
            handleGetGstReport();
        } else if (Cv.ACTION_GET_GST_REPORT_PURCHASE.equals(action)) {
            handleGetGstReportPurchase();
        } else if (Cv.ACTION_UPDATE_SALE_VOUCHER_DETAILS.equals(action)) {
            handleUpdateSaleVoucherDetails();
        } else if (Cv.ACTION_UPDATE_SALE_RETURN_VOUCHER_DETAILS.equals(action)) {
            handleUpdateSaleReturnVoucherDetails();
        } else if (Cv.ACTION_UPDATE_PURCHASE_VOUCHER_DETAILS.equals(action)) {
            handleUpdatePurchaseVoucherDetails();
        } else if (Cv.ACTION_UPDATE_PURCHASE_RETURN_VOUCHER_DETAILS.equals(action)) {
            handleUpdatePurchaseReturnVoucherDetails();
        } else if (Cv.ACTION_GET_PROFIT_AND_LOSS.equals(action)) {
            handleGetProfitAndLoss();
        } else if (Cv.ACTION_GET_GSTR3B.equals(action)) {
            handleGetGst3B();
        } else if (Cv.ACTION_GET_GSTR_1.equals(action)) {
            handleGetGst_1();
        } else if (Cv.ACTION_GET_GSTR_2.equals(action)) {
            handleGetGst_2();
        } else if (Cv.ACTION_GET_CHECK_BARCODE.equals(action)) {
            handleGetCheckBarcode();
        } else if (Cv.ACTION_GET_STOCK_TRANSFER.equals(action)) {
            handleGetStockTransfer();
        } else if (Cv.ACTION_CREATE_STOCK_TRANSFER.equals(action)) {
            handleCreateStockTransfer();
        } else if (Cv.ACTION_CREATE_AUTHORIZATION_SETTINGS.equals(action)) {
            handleCreateAuhorizationSettings();
        } else if (Cv.ACTION_DELETE_STOCK_TRANSFER.equals(action)) {
            handleDeleteStockTransfer();
        } else if (Cv.ACTION_GET_STOCK_TRANSFER_DETAILS.equals(action)) {
            handleGetStockTransferDetails();
        } else if (Cv.ACTION_GET_SERIAL_NUMBER_REFERENCE.equals(action)) {
            handleGetSerialNumberReference();
        } else if (Cv.ACTION_GET_PDF.equals(action)) {
            handleGetPdf();
        } else if (Cv.ACTION_GET_BALANCE_SHEET_PDF.equals(action)) {
            handleGetBalanceSheetPdf();
        } else if (Cv.ACTION_GET_ITEM_WISE_REPORT.equals(action)) {
            handleGetItemWiseReport();
        } else if (Cv.ACTION_GET_SALE_VOUCHERS_ITEM.equals(action)) {
            handleGetSaleVouchersItem();
        } else if (Cv.ACTION_GET_SALE_VOUCHERS_ITEM_DETAILS.equals(action)) {
            handleGetSaleVouchersItemDetails();
        } else if (Cv.ACTION_GET_PURCHASE_VOUCHERS_ITEM.equals(action)) {
            handleGetPurchaseVouchersItem();
        } else if (Cv.ACTION_GET_PURCHASE_VOUCHERS_ITEM_DETAILS.equals(action)) {
            handleGetPurchaseVouchersItemDetails();
        } else if (Cv.ACTION_CREATE_INVOICE_FORMAT.equals(action)) {
            handleCompanyInvoice();
        } else if (Cv.ACTION_VOUCHER_SERIES.equals(action)) {
            handleGetVoucherSeries();
        } else if (Cv.ACTION_CREATE_POS_VOUCHER.equals(action)) {
            handlePosVoucher();
        } else if (Cv.ACTION_UPDATE_POS_VOUCHER_DETAILS.equals(action)) {
            handleUpdatePosVoucher();
        }
        /*else if(Cv.ACTION_UPDATE_STOCK_TRANSFER.equals(action));{
            handleUpdateStockTransfer();
        }*/


    }

    private void handleGetItemGroupDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getitemgroupdetails(appUser.edit_group_id1).enqueue(new Callback<GetItemGroupDetailsResponse>() {
            @Override
            public void onResponse(Call<GetItemGroupDetailsResponse> call, Response<GetItemGroupDetailsResponse> r) {
                if (r.code() == 200) {
                    GetItemGroupDetailsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetItemGroupDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeleteItemGroup() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deleteitemgroup(appUser.delete_item_group_id).enqueue(new Callback<DeleteItemGroupReponse>() {
            @Override
            public void onResponse(Call<DeleteItemGroupReponse> call, Response<DeleteItemGroupReponse> r) {
                if (r.code() == 200) {
                    DeleteItemGroupReponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteItemGroupReponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetItemDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getitemdetails(appUser.edit_item_id).enqueue(new Callback<GetItemDetailsResponse>() {
            @Override
            public void onResponse(Call<GetItemDetailsResponse> call, Response<GetItemDetailsResponse> r) {
                if (r.code() == 200) {
                    GetItemDetailsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetItemDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleEditItemGroup() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.edititemgroup(new RequestCreateItemGroup(this), appUser.edit_group_id1).enqueue(new Callback<EditItemGroupResponse>() {
            @Override
            public void onResponse(Call<EditItemGroupResponse> call, Response<EditItemGroupResponse> r) {
                if (r.code() == 200) {
                    EditItemGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditItemGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeleteItem() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deleteitem(appUser.delete_item_id).enqueue(new Callback<DeleteItemResponse>() {
            @Override
            public void onResponse(Call<DeleteItemResponse> call, Response<DeleteItemResponse> r) {
                if (r.code() == 200) {
                    DeleteItemResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteItemResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetItemGroup() {
        api.getitemgroup(Preferences.getInstance(this).getCid()).enqueue(new Callback<GetItemGroupResponse>() {
            @Override
            public void onResponse(Call<GetItemGroupResponse> call, Response<GetItemGroupResponse> r) {
                if (r.code() == 200) {
                    GetItemGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }


            @Override
            public void onFailure(Call<GetItemGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCreateItem() {
        api.createitem(new RequestCreateItem(this)).enqueue(new Callback<CreateItemResponse>() {
            @Override
            public void onResponse(Call<CreateItemResponse> call, Response<CreateItemResponse> r) {
                if (r.code() == 200) {
                    CreateItemResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateItemResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetPurchaseType() {
        api.getpurchasetype().enqueue(new Callback<GetPurchaseTypeResponse>() {
            @Override
            public void onResponse(Call<GetPurchaseTypeResponse> call, Response<GetPurchaseTypeResponse> r) {
                if (r.code() == 200) {
                    GetPurchaseTypeResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetPurchaseTypeResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetSaleType() {
        api.getsaletype().enqueue(new Callback<GetSaleTypeResponse>() {
            @Override
            public void onResponse(Call<GetSaleTypeResponse> call, Response<GetSaleTypeResponse> r) {
                if (r.code() == 200) {
                    GetSaleTypeResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetSaleTypeResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void hadleGetTaxCategory() {
        api.gettaxcategory().enqueue(new Callback<GetTaxCategoryResponse>() {
            @Override
            public void onResponse(Call<GetTaxCategoryResponse> call, Response<GetTaxCategoryResponse> r) {
                if (r.code() == 200) {
                    GetTaxCategoryResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetTaxCategoryResponse> call, Throwable t) {

                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCreateItemGroup() {
        api.createitemgroup(new RequestCreateItemGroup(this)).enqueue(new Callback<CreateItemGroupResponse>() {
            @Override
            public void onResponse(Call<CreateItemGroupResponse> call, Response<CreateItemGroupResponse> r) {
                if (r.code() == 200) {
                    CreateItemGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateItemGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCreateBankCashDeposit() {
        api.createbankcashdeposit(new RequestCreateBankCashDeposit(this), Preferences.getInstance(this).getCid()).enqueue(new Callback<CreateBankCashDepositResponse>() {
            @Override
            public void onResponse(Call<CreateBankCashDepositResponse> call, Response<CreateBankCashDepositResponse> r) {
                if (r.code() == 200) {
                    CreateBankCashDepositResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateBankCashDepositResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCreateBankCashWithdraw() {
        api.createbankcashwithdraw(new RequestCreateBankCashWithdraw(this), Preferences.getInstance(this).getCid()).enqueue(new Callback<CreateBankCashWithdrawResponse>() {
            @Override
            public void onResponse(Call<CreateBankCashWithdrawResponse> call, Response<CreateBankCashWithdrawResponse> r) {
                if (r.code() == 200) {
                    CreateBankCashWithdrawResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateBankCashWithdrawResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetBankCashWithdraw() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getbankcashwithdraw(Preferences.getInstance(this).getCid(), appUser.start_date, appUser.end_date).enqueue(new Callback<GetBankCashWithdrawResponse>() {
            @Override
            public void onResponse(Call<GetBankCashWithdrawResponse> call, Response<GetBankCashWithdrawResponse> r) {
                if (r.code() == 200) {
                    GetBankCashWithdrawResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetBankCashWithdrawResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeleteBankCashWithdraw() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deletebankcashwithdraw(appUser.delete_bank_cash_withdraw_id).enqueue(new Callback<DeleteBankCashWithdrawResponse>() {
            @Override
            public void onResponse(Call<DeleteBankCashWithdrawResponse> call, Response<DeleteBankCashWithdrawResponse> r) {
                if (r.code() == 200) {
                    DeleteBankCashWithdrawResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteBankCashWithdrawResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetBankCashWithdrawDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getbankcashwithdrawdetails(appUser.edit_bank_cash_withdraw_id).enqueue(new Callback<GetBankCashWithdrawDetailsResponse>() {
            @Override
            public void onResponse(Call<GetBankCashWithdrawDetailsResponse> call, Response<GetBankCashWithdrawDetailsResponse> r) {
                if (r.code() == 200) {
                    GetBankCashWithdrawDetailsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetBankCashWithdrawDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleEditBankCashWithdraw() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editbankcashwithdraw(new RequestCreateBankCashWithdraw(this), appUser.edit_bank_cash_withdraw_id).enqueue(new Callback<EditBankCashWithdrawResponse>() {
            @Override
            public void onResponse(Call<EditBankCashWithdrawResponse> call, Response<EditBankCashWithdrawResponse> r) {
                if (r.code() == 200) {
                    EditBankCashWithdrawResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditBankCashWithdrawResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCreateIncome() {
        api.createincome(new RequestCreateIncome(this), Preferences.getInstance(this).getCid()).enqueue(new Callback<CreateIncomeResponse>() {
            @Override
            public void onResponse(Call<CreateIncomeResponse> call, Response<CreateIncomeResponse> r) {
                if (r.code() == 200) {
                    CreateIncomeResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateIncomeResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetIncome() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getincome(Preferences.getInstance(this).getCid(), appUser.start_date, appUser.end_date).enqueue(new Callback<GetIncomeResponse>() {
            @Override
            public void onResponse(Call<GetIncomeResponse> call, Response<GetIncomeResponse> r) {
                if (r.code() == 200) {
                    GetIncomeResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetIncomeResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeleteIncome() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deleteincome(appUser.delete_income_id).enqueue(new Callback<DeleteIncomeResponse>() {
            @Override
            public void onResponse(Call<DeleteIncomeResponse> call, Response<DeleteIncomeResponse> r) {
                if (r.code() == 200) {
                    DeleteIncomeResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteIncomeResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetIncomeDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getincomedetails(appUser.edit_income_id).enqueue(new Callback<GetIncomeDetailsResponse>() {
            @Override
            public void onResponse(Call<GetIncomeDetailsResponse> call, Response<GetIncomeDetailsResponse> response) {
                if (response.code() == 200) {
                    GetIncomeDetailsResponse body = response.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetIncomeDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleEditIncome() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editincome(new RequestCreateIncome(this), appUser.edit_income_id).enqueue(new Callback<EditIncomeResponse>() {
            @Override
            public void onResponse(Call<EditIncomeResponse> call, Response<EditIncomeResponse> r) {
                if (r.code() == 200) {
                    EditIncomeResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditIncomeResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCreateExpence() {
        api.createexpence(new RequestCreateEXpence(this), Preferences.getInstance(this).getCid()).enqueue(new Callback<CreateExpenceResponse>() {
            @Override
            public void onResponse(Call<CreateExpenceResponse> call, Response<CreateExpenceResponse> r) {
                if (r.code() == 200) {
                    CreateExpenceResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateExpenceResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetExpence() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getexpence(Preferences.getInstance(this).getCid(), appUser.start_date, appUser.end_date).enqueue(new Callback<GetExpenceResponse>() {
            @Override
            public void onResponse(Call<GetExpenceResponse> call, Response<GetExpenceResponse> r) {
                if (r.code() == 200) {
                    GetExpenceResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetExpenceResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeletePayment() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deletepayment(appUser.delete_payment_id).enqueue(new Callback<DeletePaymentResponse>() {
            @Override
            public void onResponse(Call<DeletePaymentResponse> call, Response<DeletePaymentResponse> r) {
                if (r.code() == 200) {
                    DeletePaymentResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeletePaymentResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetPaymentDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getpaymentdetails(appUser.edit_payment_id).enqueue(new Callback<GetPaymentDetailsResponse>() {
            @Override
            public void onResponse(Call<GetPaymentDetailsResponse> call, Response<GetPaymentDetailsResponse> response) {
                if (response.code() == 200) {
                    GetPaymentDetailsResponse body = response.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetPaymentDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleEditPayment() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editpayment(new RequestCreatePayment(this), appUser.edit_payment_id).enqueue(new Callback<EditPaymentResponse>() {
            @Override
            public void onResponse(Call<EditPaymentResponse> call, Response<EditPaymentResponse> r) {
                if (r.code() == 200) {
                    EditPaymentResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditPaymentResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCreateJournalVoucher() {
        api.createjournalvoucher(new RequestCreateJournalVoucher(this), Preferences.getInstance(this).getCid()).enqueue(new Callback<CreateJournalVoucherResponse>() {
            @Override
            public void onResponse(Call<CreateJournalVoucherResponse> call, Response<CreateJournalVoucherResponse> r) {
                if (r.code() == 200) {
                    CreateJournalVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateJournalVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetJournalVoucher() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getjournalvoucher(Preferences.getInstance(this).getCid(), appUser.start_date, appUser.end_date).enqueue(new Callback<GetJournalVoucherResponse>() {
            @Override
            public void onResponse(Call<GetJournalVoucherResponse> call, Response<GetJournalVoucherResponse> r) {
                if (r.code() == 200) {
                    GetJournalVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetJournalVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeleteJournalVoucher() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deletejournalvoucher(appUser.delete_journal_voucher_id).enqueue(new Callback<DeleteJournalVoucherResponse>() {
            @Override
            public void onResponse(Call<DeleteJournalVoucherResponse> call, Response<DeleteJournalVoucherResponse> r) {
                if (r.code() == 200) {
                    DeleteJournalVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteJournalVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetJournalVoucherDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getjournalvoucherdetails(appUser.edit_journal_voucher_id).enqueue(new Callback<GetJournalVoucherDetailsResponse>() {
            @Override
            public void onResponse(Call<GetJournalVoucherDetailsResponse> call, Response<GetJournalVoucherDetailsResponse> response) {
                if (response.code() == 200) {
                    GetJournalVoucherDetailsResponse body = response.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetJournalVoucherDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleEditJournalVoucher() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editjournalvoucher(new RequestCreateJournalVoucher(this), appUser.edit_journal_voucher_id).enqueue(new Callback<EditJournalVoucherResponse>() {
            @Override
            public void onResponse(Call<EditJournalVoucherResponse> call, Response<EditJournalVoucherResponse> r) {
                if (r.code() == 200) {
                    EditJournalVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditJournalVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCreateReceipt() {
        api.createreceipt(new RequestCreateReceipt(this), Preferences.getInstance(this).getCid()).enqueue(new Callback<CreateReceiptVoucherResponse>() {
            @Override
            public void onResponse(Call<CreateReceiptVoucherResponse> call, Response<CreateReceiptVoucherResponse> r) {
                if (r.code() == 200) {
                    CreateReceiptVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateReceiptVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetreceiptVoucher() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getreceiptvoucher(Preferences.getInstance(this).getCid(), appUser.start_date, appUser.end_date).enqueue(new Callback<GetReceiptVoucherResponse>() {
            @Override
            public void onResponse(Call<GetReceiptVoucherResponse> call, Response<GetReceiptVoucherResponse> r) {
                if (r.code() == 200) {
                    GetReceiptVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetReceiptVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeleteReceiptVoucher() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deletereceiptvoucher(appUser.delete_receipt_id).enqueue(new Callback<DeleteReceiptVoucherResponse>() {
            @Override
            public void onResponse(Call<DeleteReceiptVoucherResponse> call, Response<DeleteReceiptVoucherResponse> r) {
                if (r.code() == 200) {
                    DeleteReceiptVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteReceiptVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetReceiptVoucherDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getreceiptvoucherdetails(appUser.edit_receipt_id).enqueue(new Callback<GetReceiptVoucherDetailsResponse>() {
            @Override
            public void onResponse(Call<GetReceiptVoucherDetailsResponse> call, Response<GetReceiptVoucherDetailsResponse> response) {
                if (response.code() == 200) {
                    GetReceiptVoucherDetailsResponse body = response.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetReceiptVoucherDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleEditReceiptVoucher() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editreceiptvoucher(new RequestCreateReceipt(this), appUser.edit_receipt_id).enqueue(new Callback<EditReceiptVoucherResponse>() {
            @Override
            public void onResponse(Call<EditReceiptVoucherResponse> call, Response<EditReceiptVoucherResponse> r) {
                if (r.code() == 200) {
                    EditReceiptVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditReceiptVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCreateCreditNote() {
        api.createcreditnote(new RequestCreateCreditNote(this), Preferences.getInstance(this).getCid()).enqueue(new Callback<CreateCreditNoteResponse>() {
            @Override
            public void onResponse(Call<CreateCreditNoteResponse> call, Response<CreateCreditNoteResponse> r) {
                if (r.code() == 200) {
                    CreateCreditNoteResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCreditNoteResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetCreditNote() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getcreditnote(Preferences.getInstance(this).getCid(), appUser.start_date, appUser.end_date).enqueue(new Callback<GetCreditNoteResponse>() {
            @Override
            public void onResponse(Call<GetCreditNoteResponse> call, Response<GetCreditNoteResponse> r) {
                if (r.code() == 200) {
                    GetCreditNoteResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetCreditNoteResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeleteCreditNote() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deletecrdeitnote(appUser.delete_credit_note_id).enqueue(new Callback<DeleteCreditNoteResponse>() {
            @Override
            public void onResponse(Call<DeleteCreditNoteResponse> call, Response<DeleteCreditNoteResponse> r) {
                if (r.code() == 200) {
                    DeleteCreditNoteResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteCreditNoteResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetCreditNoteDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getcreditnotedetails(appUser.edit_credit_note_id).enqueue(new Callback<GetCreditNoteDetailsResponse>() {
            @Override
            public void onResponse(Call<GetCreditNoteDetailsResponse> call, Response<GetCreditNoteDetailsResponse> response) {
                if (response.code() == 200) {
                    GetCreditNoteDetailsResponse body = response.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetCreditNoteDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleEditCreditNote() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editcreditnote(new RequestCreateCreditNote(this), appUser.edit_credit_note_id).enqueue(new Callback<EditCreditNoteResponse>() {
            @Override
            public void onResponse(Call<EditCreditNoteResponse> call, Response<EditCreditNoteResponse> r) {
                if (r.code() == 200) {
                    EditCreditNoteResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditCreditNoteResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCreateDebitNote() {
        api.createdebitnote(new RequestCreateDebitNote(this), Preferences.getInstance(this).getCid()).enqueue(new Callback<CreateDebitNoteResponse>() {
            @Override
            public void onResponse(Call<CreateDebitNoteResponse> call, Response<CreateDebitNoteResponse> r) {
                if (r.code() == 200) {
                    CreateDebitNoteResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateDebitNoteResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetDebitNote() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getdebitnote(Preferences.getInstance(this).getCid(), appUser.start_date, appUser.end_date).enqueue(new Callback<GetDebitNoteResponse>() {
            @Override
            public void onResponse(Call<GetDebitNoteResponse> call, Response<GetDebitNoteResponse> r) {
                if (r.code() == 200) {
                    GetDebitNoteResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetDebitNoteResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeleteDebitNote() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deletedebitnote(appUser.delete_debit_note_id).enqueue(new Callback<DeleteDebitNoteResponse>() {
            @Override
            public void onResponse(Call<DeleteDebitNoteResponse> call, Response<DeleteDebitNoteResponse> r) {
                if (r.code() == 200) {
                    DeleteDebitNoteResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteDebitNoteResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetDebitNoteDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getdebitnotedetails(appUser.edit_debit_note_id).enqueue(new Callback<GetDebitNoteDetailsResponse>() {
            @Override
            public void onResponse(Call<GetDebitNoteDetailsResponse> call, Response<GetDebitNoteDetailsResponse> response) {
                if (response.code() == 200) {
                    GetDebitNoteDetailsResponse body = response.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetDebitNoteDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleEditDebitNote() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editdebitnote(new RequestCreateDebitNote(this), appUser.edit_debit_note_id).enqueue(new Callback<EditDebitNoteResponse>() {
            @Override
            public void onResponse(Call<EditDebitNoteResponse> call, Response<EditDebitNoteResponse> r) {
                if (r.code() == 200) {
                    EditDebitNoteResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditDebitNoteResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }


    private void handleDeleteExpence() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deleteexpence(appUser.delete_expence_id).enqueue(new Callback<DeleteExpenceResponse>() {
            @Override
            public void onResponse(Call<DeleteExpenceResponse> call, Response<DeleteExpenceResponse> r) {
                if (r.code() == 200) {
                    DeleteExpenceResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteExpenceResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetExpenceDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getexpencedetails(appUser.edit_expence_id).enqueue(new Callback<GetExpenceDetailsResponse>() {
            @Override
            public void onResponse(Call<GetExpenceDetailsResponse> call, Response<GetExpenceDetailsResponse> response) {
                if (response.code() == 200) {
                    GetExpenceDetailsResponse body = response.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetExpenceDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleEditExpence() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editexpence(new RequestCreateEXpence(this), appUser.edit_expence_id).enqueue(new Callback<EditExpenceResponse>() {
            @Override
            public void onResponse(Call<EditExpenceResponse> call, Response<EditExpenceResponse> r) {
                if (r.code() == 200) {
                    EditExpenceResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditExpenceResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }


    private void handleCreatePayment() {
        api.createpayment(new RequestCreatePayment(this), Preferences.getInstance(this).getCid()).enqueue(new Callback<CreatePaymentResponse>() {
            @Override
            public void onResponse(Call<CreatePaymentResponse> call, Response<CreatePaymentResponse> r) {
                if (r.code() == 200) {
                    CreatePaymentResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreatePaymentResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetPayment() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getpayment(Preferences.getInstance(this).getCid(), appUser.start_date, appUser.end_date).enqueue(new Callback<GetPaymentResponse>() {
            @Override
            public void onResponse(Call<GetPaymentResponse> call, Response<GetPaymentResponse> r) {
                if (r.code() == 200) {
                    GetPaymentResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetPaymentResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }


    private void handleGetBankCashDeposit() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getbankcashdeposit(Preferences.getInstance(this).getCid(), appUser.start_date, appUser.end_date).enqueue(new Callback<GetBankCashDepositResponse>() {
            @Override
            public void onResponse(Call<GetBankCashDepositResponse> call, Response<GetBankCashDepositResponse> r) {
                if (r.code() == 200) {
                    GetBankCashDepositResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetBankCashDepositResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeleteBankCashDeposit() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deletebankcashdeposit(appUser.delete_bank_cash_deposit_id).enqueue(new Callback<DeleteBankCashDepositResponse>() {
            @Override
            public void onResponse(Call<DeleteBankCashDepositResponse> call, Response<DeleteBankCashDepositResponse> r) {
                if (r.code() == 200) {
                    DeleteBankCashDepositResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteBankCashDepositResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetBankCashDepositDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getbankcashdepositdetails(appUser.edit_bank_cash_deposit_id).enqueue(new Callback<GetBankCashDepositDetailsResponse>() {
            @Override
            public void onResponse(Call<GetBankCashDepositDetailsResponse> call, Response<GetBankCashDepositDetailsResponse> r) {
                if (r.code() == 200) {
                    GetBankCashDepositDetailsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetBankCashDepositDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleEditBankCashDeposit() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editbankcashdeposit(new RequestCreateBankCashDeposit(this), appUser.edit_bank_cash_deposit_id).enqueue(new Callback<EditBankCashDepositResponse>() {
            @Override
            public void onResponse(Call<EditBankCashDepositResponse> call, Response<EditBankCashDepositResponse> r) {
                if (r.code() == 200) {
                    EditBankCashDepositResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditBankCashDepositResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleRegister() {
        api.register(new RequestRegister(this)).enqueue(new LocalCallback() {
            @Override
            public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> r) {
                processUserApiResponse(r);
            }

            @Override
            public void onFailure(Call<UserApiResponse> call, Throwable t) {
                try {

                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleUpdateUser() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.updateuser(new RequestUpdateUser(this)).enqueue(new Callback<UserApiResponse>() {
            @Override
            public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> r) {
                if (r.code() == 200) {
                    UserApiResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<UserApiResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleForgotPassword() {

        api.forgotpassword(new RequestForgotPassword(this)).enqueue(new LocalCallback() {
            @Override
            public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> r) {
                processUserApiResponse(r);

            }

            @Override
            public void onFailure(Call<UserApiResponse> call, Throwable t) {
                try {

                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleVerifyOtp() {

        api.verifyotp(new RequestVerification(this)).enqueue(new LocalCallback() {
            @Override
            public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> r) {
                processUserApiResponse(r);

            }

            @Override
            public void onFailure(Call<UserApiResponse> call, Throwable t) {
                try {

                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleNewPassword() {

        api.newpassword(new RequestNewPassword(this)).enqueue(new LocalCallback() {
            @Override
            public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> r) {
                processUserApiResponse(r);

            }

            @Override
            public void onFailure(Call<UserApiResponse> call, Throwable t) {
                try {

                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleUpdateMobileNumber() {

        api.updatemobile(new RequestUpdateMobileNumber(this)).enqueue(new LocalCallback() {
            @Override
            public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> r) {
                processUserApiResponse(r);

            }

            @Override
            public void onFailure(Call<UserApiResponse> call, Throwable t) {
                try {

                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handlefacebookcheck() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.exist(appUser.fb_id).enqueue(new Callback<UserExistResponse>() {
            @Override
            public void onResponse(Call<UserExistResponse> call, Response<UserExistResponse> r) {
                if (r.code() == 200) {
                    UserExistResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<UserExistResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleResendOtp() {

        api.resendotp(new RequestResendOtp(this)).enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> r) {
                if (r.code() == 200) {
                    OtpResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                try {

                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleLogin() {

        api.login(new RequestLoginEmail(this)).enqueue(new LocalCallback() {
            @Override
            public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> r) {
                processUserApiResponse(r);

            }

            @Override
            public void onFailure(Call<UserApiResponse> call, Throwable t) {
                try {

                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCreateCompany() {
        api.createcompany(new RequestCreateCompany(this)).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleBasic() {
        api.cbasiccompany(new RequestBasic(this), Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetItemmaterrialCentre() {
        api.getitemmaterial_center(Preferences.getInstance(getApplicationContext()).getCid(), Preferences.getInstance(getApplicationContext()).getStoreId()).enqueue(new Callback<GetItemResponse>() {
            @Override
            public void onResponse(Call<GetItemResponse> call, Response<GetItemResponse> r) {
                if (r.code() == 200) {
                    GetItemResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetItemResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetItem() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getitem(Preferences.getInstance(getApplicationContext()).getCid(), appUser.stock_in_hand_date).enqueue(new Callback<GetItemResponse>() {
            @Override
            public void onResponse(Call<GetItemResponse> call, Response<GetItemResponse> r) {
                if (r.code() == 200) {
                    GetItemResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetItemResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCompanyDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.cdetails(new RequestCompanyDetails(this), Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCompanyBankDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.bankDetails(new RequestCompanyBankDetails(this), Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCompanyGst() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.cgst(new RequestCompanyGst(this), Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCompanyAdditional() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.cadditional(new RequestCompanyAdditional(this), Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCompanyLogo() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.clogo(new RequestCompanyLogo(this), Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCompanySignature() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.csignature(new RequestCompanySignature(this), Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleEditLogin() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.ceditlogin(new RequestEditLogin(this), appUser.company_user_id).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCompanyLogin() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.clogin(new RequestCompanyLogin(this), Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CompanyLoginResponse>() {
            @Override
            public void onResponse(Call<CompanyLoginResponse> call, Response<CompanyLoginResponse> r) {
                if (r.code() == 200) {
                    CompanyLoginResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CompanyLoginResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCompanyList() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getCompanyList(appUser.user_id).enqueue(new Callback<CompanyListResponse>() {
            @Override
            public void onResponse(Call<CompanyListResponse> call, Response<CompanyListResponse> r) {
                if (r.code() == 200) {
                    CompanyListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CompanyListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleDeleteCompany() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.cdelete(appUser.company_id).enqueue(new Callback<DeleteCompanyResponse>() {
            @Override
            public void onResponse(Call<DeleteCompanyResponse> call, Response<DeleteCompanyResponse> r) {
                if (r.code() == 200) {
                    DeleteCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetIndustry() {
        api.getIndustry().enqueue(new Callback<IndustryTypeResponse>() {
            @Override
            public void onResponse(Call<IndustryTypeResponse> call, Response<IndustryTypeResponse> r) {
                if (r.code() == 200) {
                    IndustryTypeResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<IndustryTypeResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleAuthenticateCompany() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.cauthenticate(new RequestCompanyAuthenticate(this), appUser.company_id).enqueue(new Callback<CompanyAuthenticateResponse>() {
            @Override
            public void onResponse(Call<CompanyAuthenticateResponse> call, Response<CompanyAuthenticateResponse> r) {
                if (r.code() == 200) {
                    CompanyAuthenticateResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CompanyAuthenticateResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetCompany() {
        api.getcompany(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CompanyResponse>() {
            @Override
            public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> r) {
                if (r.code() == 200) {
                    CompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleSearchCompany() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.searchcompany(appUser.search_company_id, appUser.mobile).enqueue(new Callback<CompanyResponse>() {
            @Override
            public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> r) {
                if (r.code() == 200) {
                    CompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetCompanyUser() {
        api.getcompanyusers(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CompanyUserResponse>() {
            @Override
            public void onResponse(Call<CompanyUserResponse> call, Response<CompanyUserResponse> r) {
                if (r.code() == 200) {
                    CompanyUserResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CompanyUserResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCreateAccountGroup() {
        api.createaccountgroup(new RequestCreateAccountGroup(this)).enqueue(new Callback<CreateAccountGroupResponse>() {
            @Override
            public void onResponse(Call<CreateAccountGroupResponse> call, Response<CreateAccountGroupResponse> r) {
                if (r.code() == 200) {
                    CreateAccountGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateAccountGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetAccountGroup() {
        api.getaccountgroup(Preferences.getInstance(this).getCid()).enqueue(new Callback<GetAccountGroupResponse>() {
            @Override
            public void onResponse(Call<GetAccountGroupResponse> call, Response<GetAccountGroupResponse> r) {
                if (r.code() == 200) {
                    GetAccountGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetAccountGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetAccountGroupDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getaccountgroupdetails(appUser.edit_group_id).enqueue(new Callback<GetAccountGroupDetailsResponse>() {
            @Override
            public void onResponse(Call<GetAccountGroupDetailsResponse> call, Response<GetAccountGroupDetailsResponse> r) {
                if (r.code() == 200) {
                    GetAccountGroupDetailsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetAccountGroupDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleEditAccountGroup() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editaccountgroup(new RequestCreateAccountGroup(this), appUser.edit_group_id).enqueue(new Callback<EditAccountGroupResponse>() {
            @Override
            public void onResponse(Call<EditAccountGroupResponse> call, Response<EditAccountGroupResponse> r) {
                if (r.code() == 200) {
                    EditAccountGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditAccountGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCreateAccount() {
        api.createaccount(new RequestCreateAccount(this)).enqueue(new Callback<CreateAccountResponse>() {
            @Override
            public void onResponse(Call<CreateAccountResponse> call, Response<CreateAccountResponse> r) {
                if (r.code() == 200) {
                    CreateAccountResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateAccountResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetAccount() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getaccount(Preferences.getInstance(getApplicationContext()).getCid(), appUser.account_master_group).enqueue(new Callback<GetAccountResponse>() {
            @Override
            public void onResponse(Call<GetAccountResponse> call, Response<GetAccountResponse> r) {
                if (r.code() == 200) {
                    GetAccountResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetAccountResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetAccountDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getaccountdetails(appUser.edit_account_id).enqueue(new Callback<GetAccountDetailsResponse>() {
            @Override
            public void onResponse(Call<GetAccountDetailsResponse> call, Response<GetAccountDetailsResponse> r) {
                if (r.code() == 200) {
                    GetAccountDetailsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetAccountDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleEditAccount() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editaccount(new RequestCreateAccount(this), appUser.edit_account_id).enqueue(new Callback<EditAccountResponse>() {
            @Override
            public void onResponse(Call<EditAccountResponse> call, Response<EditAccountResponse> r) {
                if (r.code() == 200) {
                    EditAccountResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditAccountResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleEditItem() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.edititem(new RequestCreateItem(this), appUser.edit_item_id).enqueue(new Callback<EditItemResponse>() {
            @Override
            public void onResponse(Call<EditItemResponse> call, Response<EditItemResponse> r) {
                if (r.code() == 200) {
                    EditItemResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditItemResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeleteAccount() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deleteaccount(appUser.delete_account_id).enqueue(new Callback<DeleteAccountResponse>() {
            @Override
            public void onResponse(Call<DeleteAccountResponse> call, Response<DeleteAccountResponse> r) {
                if (r.code() == 200) {
                    DeleteAccountResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteAccountResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleDeleteAccountGroup() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deleteaccountgroup(appUser.delete_group_id).enqueue(new Callback<DeleteAccountGroupResponse>() {
            @Override
            public void onResponse(Call<DeleteAccountGroupResponse> call, Response<DeleteAccountGroupResponse> r) {
                if (r.code() == 200) {
                    DeleteAccountGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteAccountGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetPackages() {
        api.getpackage().enqueue(new Callback<GetPackageResponse>() {
            @Override
            public void onResponse(Call<GetPackageResponse> call, Response<GetPackageResponse> r) {
                if (r.code() == 200) {
                    GetPackageResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetPackageResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handlePlans() {
        api.plan(new RequestPlan(this)).enqueue(new Callback<PlanResponse>() {
            @Override
            public void onResponse(Call<PlanResponse> call, Response<PlanResponse> r) {
                if (r.code() == 200) {
                    PlanResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<PlanResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetMaterialCentreGroupList() {
        api.getmaterialcentregrouplist(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<GetMaterialCentreGroupListResponse>() {
            @Override
            public void onResponse(Call<GetMaterialCentreGroupListResponse> call, Response<GetMaterialCentreGroupListResponse> r) {
                if (r.code() == 200) {
                    GetMaterialCentreGroupListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetMaterialCentreGroupListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCreateMaterialCentreGroup() {
        api.creatematerialcentregroup(new RequestCreateMaterialCentreGroup(this)).enqueue(new Callback<CreateMaterialCentreGroupResponse>() {
            @Override
            public void onResponse(Call<CreateMaterialCentreGroupResponse> call, Response<CreateMaterialCentreGroupResponse> r) {
                if (r.code() == 200) {
                    CreateMaterialCentreGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateMaterialCentreGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleEditMaterialCentreGroup() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editmaterialcentregroup(new RequestCreateMaterialCentreGroup(this), appUser.edit_material_centre_group_id).enqueue(new Callback<EditMaterialCentreGroupResponse>() {
            @Override
            public void onResponse(Call<EditMaterialCentreGroupResponse> call, Response<EditMaterialCentreGroupResponse> r) {
                if (r.code() == 200) {
                    EditMaterialCentreGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditMaterialCentreGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleDeleteMaterialCentreGroup() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deletematerialcentregroup(appUser.delete_material_centre_group_id).enqueue(new Callback<DeleteMaterialCentreGroupResponse>() {
            @Override
            public void onResponse(Call<DeleteMaterialCentreGroupResponse> call, Response<DeleteMaterialCentreGroupResponse> r) {
                if (r.code() == 200) {
                    DeleteMaterialCentreGroupResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteMaterialCentreGroupResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetMaterialCentreGroupDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getmaterialcentregroupdetails(appUser.edit_material_centre_group_id).enqueue(new Callback<GetMaterialCentreGroupDetailResponse>() {
            @Override
            public void onResponse(Call<GetMaterialCentreGroupDetailResponse> call, Response<GetMaterialCentreGroupDetailResponse> r) {
                if (r.code() == 200) {
                    GetMaterialCentreGroupDetailResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetMaterialCentreGroupDetailResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetMaterialCentreList() {
        api.getmaterialcentrelist(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<GetMaterialCentreListResponse>() {
            @Override
            public void onResponse(Call<GetMaterialCentreListResponse> call, Response<GetMaterialCentreListResponse> r) {
                if (r.code() == 200) {
                    GetMaterialCentreListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetMaterialCentreListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCreateMaterialCentre() {
        api.creatematerialcentre(new RequestCreateMaterialCentre(this)).enqueue(new Callback<CreateMaterialCentreResponse>() {
            @Override
            public void onResponse(Call<CreateMaterialCentreResponse> call, Response<CreateMaterialCentreResponse> r) {
                if (r.code() == 200) {
                    CreateMaterialCentreResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateMaterialCentreResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleEditMaterialCentre() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editmaterialcentre(new RequestCreateMaterialCentre(this), appUser.edit_material_centre_id).enqueue(new Callback<EditMaterialCentreReponse>() {
            @Override
            public void onResponse(Call<EditMaterialCentreReponse> call, Response<EditMaterialCentreReponse> r) {
                if (r.code() == 200) {
                    EditMaterialCentreReponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditMaterialCentreReponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleDeleteMaterialCentre() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deletematerialcentre(appUser.delete_material_centre_id).enqueue(new Callback<DeleteMaterialCentreResponse>() {
            @Override
            public void onResponse(Call<DeleteMaterialCentreResponse> call, Response<DeleteMaterialCentreResponse> r) {
                if (r.code() == 200) {
                    DeleteMaterialCentreResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteMaterialCentreResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetMaterialCentreDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getmaterialcentredetails(appUser.edit_material_centre_id).enqueue(new Callback<GetMaterialCentreDetailResponse>() {
            @Override
            public void onResponse(Call<GetMaterialCentreDetailResponse> call, Response<GetMaterialCentreDetailResponse> r) {
                if (r.code() == 200) {
                    GetMaterialCentreDetailResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetMaterialCentreDetailResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetStock() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getstock(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<StockResponse>() {
            @Override
            public void onResponse(Call<StockResponse> call, Response<StockResponse> r) {
                if (r.code() == 200) {
                    StockResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<StockResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetUqc() {
        api.getuqc().enqueue(new Callback<GetUqcResponse>() {
            @Override
            public void onResponse(Call<GetUqcResponse> call, Response<GetUqcResponse> r) {
                if (r.code() == 200) {
                    GetUqcResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetUqcResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetUnitList() {
        api.getunitlist(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<GetUnitListResponse>() {
            @Override
            public void onResponse(Call<GetUnitListResponse> call, Response<GetUnitListResponse> r) {
                if (r.code() == 200) {
                    GetUnitListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetUnitListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCreateUnit() {
        api.createunit(new RequestCreateUnit(this)).enqueue(new Callback<CreateUnitResponse>() {
            @Override
            public void onResponse(Call<CreateUnitResponse> call, Response<CreateUnitResponse> r) {
                if (r.code() == 200) {
                    CreateUnitResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateUnitResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleEditUnit() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editunit(new RequestCreateUnit(this), appUser.edit_unit_id).enqueue(new Callback<EditUnitResponse>() {
            @Override
            public void onResponse(Call<EditUnitResponse> call, Response<EditUnitResponse> r) {
                if (r.code() == 200) {
                    EditUnitResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditUnitResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleDeleteUnit() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deleteunit(appUser.delete_unit_id).enqueue(new Callback<DeleteUnitResponse>() {
            @Override
            public void onResponse(Call<DeleteUnitResponse> call, Response<DeleteUnitResponse> r) {
                if (r.code() == 200) {
                    DeleteUnitResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteUnitResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetUnitDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getunitdetails(appUser.edit_unit_id).enqueue(new Callback<GetUnitDetailsResponse>() {
            @Override
            public void onResponse(Call<GetUnitDetailsResponse> call, Response<GetUnitDetailsResponse> r) {
                if (r.code() == 200) {
                    GetUnitDetailsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetUnitDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetUnitConversionList() {
        api.getunitconversionlist(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<GetUnitConversionListResponse>() {
            @Override
            public void onResponse(Call<GetUnitConversionListResponse> call, Response<GetUnitConversionListResponse> r) {
                if (r.code() == 200) {
                    GetUnitConversionListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetUnitConversionListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCreateUnitConversion() {
        api.createunitconversion(new RequestCreateUnitConversion(this)).enqueue(new Callback<CreateUnitConversionResponse>() {
            @Override
            public void onResponse(Call<CreateUnitConversionResponse> call, Response<CreateUnitConversionResponse> r) {
                if (r.code() == 200) {
                    CreateUnitConversionResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateUnitConversionResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleEditUnitConversion() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editunitconversion(new RequestCreateUnitConversion(this), appUser.edit_unit_conversion_id).enqueue(new Callback<EditUnitConversionResponse>() {
            @Override
            public void onResponse(Call<EditUnitConversionResponse> call, Response<EditUnitConversionResponse> r) {
                if (r.code() == 200) {
                    EditUnitConversionResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditUnitConversionResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleDeleteUnitConversion() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deleteunitconversion(appUser.delete_unit_conversion_id).enqueue(new Callback<DeleteUnitConversionResponse>() {
            @Override
            public void onResponse(Call<DeleteUnitConversionResponse> call, Response<DeleteUnitConversionResponse> r) {
                if (r.code() == 200) {
                    DeleteUnitConversionResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteUnitConversionResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetUnitConversionDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getunitconversiondetails(appUser.edit_unit_conversion_id).enqueue(new Callback<GetUnitConversionDetailsResponse>() {
            @Override
            public void onResponse(Call<GetUnitConversionDetailsResponse> call, Response<GetUnitConversionDetailsResponse> r) {
                if (r.code() == 200) {
                    GetUnitConversionDetailsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetUnitConversionDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetBillSundryList() {
        api.getbillsundrylist(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<GetBillSundryListResponse>() {
            @Override
            public void onResponse(Call<GetBillSundryListResponse> call, Response<GetBillSundryListResponse> r) {
                if (r.code() == 200) {
                    GetBillSundryListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetBillSundryListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleCreateBillSundry() {
        api.createbillsundry(new RequestCreateBillSundry(this), Preferences.getInstance(this).getCid()).enqueue(new Callback<CreateBillSundryResponse>() {
            @Override
            public void onResponse(Call<CreateBillSundryResponse> call, Response<CreateBillSundryResponse> r) {
                if (r.code() == 200) {
                    CreateBillSundryResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateBillSundryResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleSaleVoucher() {
        api.createSaleVoucher(new RequestCreateSaleVoucher(this), Preferences.getInstance(this).getCid()).enqueue(new Callback<CreateSaleVoucherResponse>() {
            @Override
            public void onResponse(Call<CreateSaleVoucherResponse> call, Response<CreateSaleVoucherResponse> r) {
                if (r.code() == 200) {
                    CreateSaleVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateSaleVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetSaleVoucherList() {
        api.getsalevoucherlist(Preferences.getInstance(this).getCid()).enqueue(new Callback<GetSaleVoucherListResponse>() {
            @Override
            public void onResponse(Call<GetSaleVoucherListResponse> call, Response<GetSaleVoucherListResponse> r) {
                if (r.code() == 200) {
                    GetSaleVoucherListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetSaleVoucherListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeleteSaleVoucher() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deletesalevoucher(appUser.delete_sale_voucher_id).enqueue(new Callback<DeleteSaleVoucherResponse>() {
            @Override
            public void onResponse(Call<DeleteSaleVoucherResponse> call, Response<DeleteSaleVoucherResponse> r) {
                if (r.code() == 200) {
                    DeleteSaleVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteSaleVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeleteBillSundry() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deletebillsundry(appUser.delete_bill_sundry_id).enqueue(new Callback<DeleteBillSundryResponse>() {
            @Override
            public void onResponse(Call<DeleteBillSundryResponse> call, Response<DeleteBillSundryResponse> r) {
                if (r.code() == 200) {
                    DeleteBillSundryResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteBillSundryResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetBillSundryDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getbillsundrydetails(appUser.edit_bill_sundry_id).enqueue(new Callback<GetBillSundryDetailsResponse>() {
            @Override
            public void onResponse(Call<GetBillSundryDetailsResponse> call, Response<GetBillSundryDetailsResponse> r) {
                if (r.code() == 200) {
                    GetBillSundryDetailsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetBillSundryDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetBillSundryNature() {
        api.getbillsundrynature(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<GetBillSundryNatureResponse>() {
            @Override
            public void onResponse(Call<GetBillSundryNatureResponse> call, Response<GetBillSundryNatureResponse> r) {
                if (r.code() == 200) {
                    GetBillSundryNatureResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetBillSundryNatureResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }


    private void handleEditBillSundry() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.editbillsundry(new RequestCreateBillSundry(this), appUser.edit_bill_sundry_id).enqueue(new Callback<EditBillSundryResponse>() {
            @Override
            public void onResponse(Call<EditBillSundryResponse> call, Response<EditBillSundryResponse> r) {
                if (r.code() == 200) {
                    EditBillSundryResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<EditBillSundryResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }


    private void processUserApiResponse(Response<UserApiResponse> response) {
        if (response.code() == 200) {
            UserApiResponse body = response.body();
            EventBus.getDefault().post(body);
        } else {
            EventBus.getDefault().post(Cv.TIMEOUT);
        }

    }


    private void handlePurchase() {
        api.createpurchase(new RequestCreatePurchase(this), Preferences.getInstance(this).getCid()).enqueue(new Callback<CreatePurchaseResponce>() {
            @Override
            public void onResponse(Call<CreatePurchaseResponce> call, Response<CreatePurchaseResponce> r) {
                if (r.code() == 200) {
                    CreatePurchaseResponce body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreatePurchaseResponce> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleSaleReturn() {
        api.createSaleReturn(new RequestCreateSaleReturn(this), Preferences.getInstance(this).getCid()).enqueue(new Callback<CreateSaleReturnResponse>() {
            @Override
            public void onResponse(Call<CreateSaleReturnResponse> call, Response<CreateSaleReturnResponse> r) {
                if (r.code() == 200) {
                    CreateSaleReturnResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateSaleReturnResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handlePurchaseReturn() {
        api.createPurchaseReturn(new RequestCreatePurchaseReturn(this), Preferences.getInstance(this).getCid()).enqueue(new Callback<CreatePurchaseReturnResponse>() {
            @Override
            public void onResponse(Call<CreatePurchaseReturnResponse> call, Response<CreatePurchaseReturnResponse> r) {
                if (r.code() == 200) {
                    CreatePurchaseReturnResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreatePurchaseReturnResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetCompanyDashboardInfo() {
        api.getcompanydashboardinfo(Preferences.getInstance(this).getCid()).enqueue(new Callback<GetCompanyDashboardInfoResponse>() {
            @Override
            public void onResponse(Call<GetCompanyDashboardInfoResponse> call, Response<GetCompanyDashboardInfoResponse> r) {
                if (r.code() == 200) {
                    GetCompanyDashboardInfoResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetCompanyDashboardInfoResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetTransactionPdf() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.gettransactionpdf(Preferences.getInstance(getApplicationContext()).getCid(), appUser.pdf_account_id, appUser.pdf_start_date, appUser.pdf_end_date).enqueue(new Callback<GetTransactionPdfResponse>() {
            @Override
            public void onResponse(Call<GetTransactionPdfResponse> call, Response<GetTransactionPdfResponse> r) {
                if (r.code() == 200) {
                    GetTransactionPdfResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetTransactionPdfResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetVoucherNumbers() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getvouchernumbers(Preferences.getInstance(getApplicationContext()).getCid(), appUser.voucher_type).enqueue(new Callback<GetVoucherNumbersResponse>() {
            @Override
            public void onResponse(Call<GetVoucherNumbersResponse> call, Response<GetVoucherNumbersResponse> r) {
                if (r.code() == 200) {
                    GetVoucherNumbersResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetVoucherNumbersResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetSaleVoucher() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getsalevoucher(Preferences.getInstance(getApplicationContext()).getCid(), appUser.start_date, appUser.end_date).enqueue(new Callback<GetSaleVoucherListResponse>() {
            @Override
            public void onResponse(Call<GetSaleVoucherListResponse> call, Response<GetSaleVoucherListResponse> r) {
                if (r.code() == 200) {
                    GetSaleVoucherListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetSaleVoucherListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetSaleReturnVoucher() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getsalereturnvoucher(Preferences.getInstance(getApplicationContext()).getCid(), appUser.start_date, appUser.end_date).enqueue(new Callback<GetSaleReturnVoucherListResponse>() {

            @Override
            public void onResponse(Call<GetSaleReturnVoucherListResponse> call, Response<GetSaleReturnVoucherListResponse> r) {
                if (r.code() == 200) {
                    GetSaleReturnVoucherListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetSaleReturnVoucherListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetPurchaseVoucher() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getpurchasevoucher(Preferences.getInstance(getApplicationContext()).getCid(), appUser.start_date, appUser.end_date).enqueue(new Callback<GetPurchaseVoucherListResponse>() {
            @Override
            public void onResponse(Call<GetPurchaseVoucherListResponse> call, Response<GetPurchaseVoucherListResponse> r) {
                if (r.code() == 200) {
                    GetPurchaseVoucherListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetPurchaseVoucherListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeleteSaleReturnVoucher() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deletesalereturnvoucher(appUser.delete_sale_return_voucher_id).enqueue(new Callback<DeleteSaleReturnVoucherResponse>() {
            @Override
            public void onResponse(Call<DeleteSaleReturnVoucherResponse> call, Response<DeleteSaleReturnVoucherResponse> r) {
                if (r.code() == 200) {
                    DeleteSaleReturnVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteSaleReturnVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetPurchaseReturnvoucher() {

        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getpurchasereturnvoucher(Preferences.getInstance(getApplicationContext()).getCid(), appUser.start_date, appUser.end_date).enqueue(new Callback<GetPurchaseReturnVoucherListResponse>() {
            @Override
            public void onResponse(Call<GetPurchaseReturnVoucherListResponse> call, Response<GetPurchaseReturnVoucherListResponse> r) {
                if (r.code() == 200) {
                    GetPurchaseReturnVoucherListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetPurchaseReturnVoucherListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleVersion() {
        AppUser appUser = LocalRepositories.getAppUser(getApplicationContext());
        api.version(appUser.device_type).enqueue(new Callback<VersionResponse>() {
            @Override
            public void onResponse(Call<VersionResponse> call, Response<VersionResponse> response) {

                if (response.code() == 200) {
                    VersionResponse body = response.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);

                }
            }

            @Override
            public void onFailure(Call<VersionResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetPdc() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getPdc(Preferences.getInstance(getApplicationContext()).getCid(), appUser.receipt_duration_spinner).enqueue(new Callback<GetPdcResponse>() {
            @Override
            public void onResponse(Call<GetPdcResponse> call, Response<GetPdcResponse> r) {
                if (r.code() == 200) {
                    GetPdcResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetPdcResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeletePurchaseVoucher() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deletepurchasevoucher(appUser.delete_purchase_voucher_id).enqueue(new Callback<DeletePurchaseVoucherResponse>() {
            @Override
            public void onResponse(Call<DeletePurchaseVoucherResponse> call, Response<DeletePurchaseVoucherResponse> r) {
                if (r.code() == 200) {
                    DeletePurchaseVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeletePurchaseVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeletePurchaseReturnVoucher() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deletepurchasereturnvoucher(appUser.delete_purchase_return_voucher_id).enqueue(new Callback<DeletePurchaseReturnVoucherResponse>() {
            @Override
            public void onResponse(Call<DeletePurchaseReturnVoucherResponse> call, Response<DeletePurchaseReturnVoucherResponse> r) {
                if (r.code() == 200) {
                    DeletePurchaseReturnVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeletePurchaseReturnVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetDefaultItems() {
        api.getDefaultItems(Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<GetDefaultItemsResponse>() {
            @Override
            public void onResponse(Call<GetDefaultItemsResponse> call, Response<GetDefaultItemsResponse> r) {
                if (r.code() == 200) {
                    GetDefaultItemsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetDefaultItemsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetSaleVoucherDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getSaleVoucherDetails(appUser.edit_sale_voucher_id).enqueue(new Callback<GetSaleVoucherDetails>() {
            @Override
            public void onResponse(Call<GetSaleVoucherDetails> call, Response<GetSaleVoucherDetails> r) {
                if (r.code() == 200) {
                    GetSaleVoucherDetails body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetSaleVoucherDetails> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCreateDefaultItems() {
        api.createDefaultItems(new RequestCreateDefaultItems(this)).enqueue(new Callback<CreateDefaultItemsResponse>() {
            @Override
            public void onResponse(Call<CreateDefaultItemsResponse> call, Response<CreateDefaultItemsResponse> r) {
                if (r.code() == 200) {
                    CreateDefaultItemsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateDefaultItemsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetPurchaseReturnVoucherDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getPurchaseReturnVoucherDetails(appUser.edit_sale_voucher_id).enqueue(new Callback<GetPurchaseReturnVoucherDetails>() {
            @Override
            public void onResponse(Call<GetPurchaseReturnVoucherDetails> call, Response<GetPurchaseReturnVoucherDetails> r) {
                if (r.code() == 200) {
                    GetPurchaseReturnVoucherDetails body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetPurchaseReturnVoucherDetails> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetPurchaseVoucherDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getPurchaseVoucherDetails(appUser.edit_sale_voucher_id).enqueue(new Callback<GetPurchaseVoucherDetails>() {
            @Override
            public void onResponse(Call<GetPurchaseVoucherDetails> call, Response<GetPurchaseVoucherDetails> r) {
                if (r.code() == 200) {
                    GetPurchaseVoucherDetails body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetPurchaseVoucherDetails> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetSaleReturnVoucherDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getSaleReturnVoucherDetails(appUser.edit_sale_voucher_id).enqueue(new Callback<GetSaleReturnVoucherDetails>() {
            @Override
            public void onResponse(Call<GetSaleReturnVoucherDetails> call, Response<GetSaleReturnVoucherDetails> r) {
                if (r.code() == 200) {
                    GetSaleReturnVoucherDetails body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetSaleReturnVoucherDetails> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleUpdateSaleVoucherDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.updateSaleVoucherDetails(new RequestCreateSaleVoucher(this), appUser.edit_sale_voucher_id).enqueue(new Callback<UpdateSaleVoucherResponse>() {
            @Override
            public void onResponse(Call<UpdateSaleVoucherResponse> call, Response<UpdateSaleVoucherResponse> r) {
                if (r.code() == 200) {
                    UpdateSaleVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<UpdateSaleVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleUpdatePurchaseReturnVoucherDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.updatePurchaseReturnVoucherDetails(new RequestCreatePurchaseReturn(this), appUser.edit_sale_voucher_id).enqueue(new Callback<UpdatePurchaseReturnVoucher>() {
            @Override
            public void onResponse(Call<UpdatePurchaseReturnVoucher> call, Response<UpdatePurchaseReturnVoucher> r) {
                if (r.code() == 200) {
                    UpdatePurchaseReturnVoucher body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<UpdatePurchaseReturnVoucher> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleUpdatePurchaseVoucherDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.updatePurchaseVoucherDetails(new RequestCreatePurchase(this), appUser.edit_sale_voucher_id).enqueue(new Callback<UpdatePurchaseResponse>() {
            @Override
            public void onResponse(Call<UpdatePurchaseResponse> call, Response<UpdatePurchaseResponse> r) {
                if (r.code() == 200) {
                    UpdatePurchaseResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<UpdatePurchaseResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleUpdateSaleReturnVoucherDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.updateSaleReturnVoucherDetails(new RequestCreateSaleReturn(this), appUser.edit_sale_voucher_id).enqueue(new Callback<UpdateSaleReturnResponse>() {
            @Override
            public void onResponse(Call<UpdateSaleReturnResponse> call, Response<UpdateSaleReturnResponse> r) {
                if (r.code() == 200) {
                    UpdateSaleReturnResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<UpdateSaleReturnResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetGstReport() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getcompanyreport(Preferences.getInstance(getApplicationContext()).getCid(), appUser.pdf_start_date, appUser.pdf_end_date).enqueue(new Callback<CompanyReportResponse>() {
            @Override
            public void onResponse(Call<CompanyReportResponse> call, Response<CompanyReportResponse> r) {
                if (r.code() == 200) {
                    CompanyReportResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else
                    EventBus.getDefault().post(Cv.TIMEOUT);
            }

            @Override
            public void onFailure(Call<CompanyReportResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetGstReportPurchase() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getgstreportpurchase(Preferences.getInstance(getApplicationContext()).getCid(), appUser.pdf_start_date, appUser.pdf_end_date).enqueue(new Callback<CompanyReportResponse>() {
            @Override
            public void onResponse(Call<CompanyReportResponse> call, Response<CompanyReportResponse> r) {
                if (r.code() == 200) {
                    CompanyReportResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else
                    EventBus.getDefault().post(Cv.TIMEOUT);
            }

            @Override
            public void onFailure(Call<CompanyReportResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetProfitAndLoss() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getprofitandloss(Preferences.getInstance(getApplicationContext()).getCid(), appUser.pdf_start_date, appUser.pdf_end_date).enqueue(new Callback<CompanyReportResponse>() {
            @Override
            public void onResponse(Call<CompanyReportResponse> call, Response<CompanyReportResponse> r) {
                if (r.code() == 200) {
                    CompanyReportResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else
                    EventBus.getDefault().post(Cv.TIMEOUT);
            }

            @Override
            public void onFailure(Call<CompanyReportResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetGst3B() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getgstr3b(Preferences.getInstance(getApplicationContext()).getCid(), appUser.pdf_start_date, appUser.pdf_end_date).enqueue(new Callback<CompanyReportResponse>() {
            @Override
            public void onResponse(Call<CompanyReportResponse> call, Response<CompanyReportResponse> r) {
                if (r.code() == 200) {
                    CompanyReportResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else
                    EventBus.getDefault().post(Cv.TIMEOUT);
            }

            @Override
            public void onFailure(Call<CompanyReportResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetCheckBarcode() {
        api.checkbarcode(new RequestCheckBarcode(this), Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CheckBarcodeResponse>() {
            @Override
            public void onResponse(Call<CheckBarcodeResponse> call, Response<CheckBarcodeResponse> r) {
                if (r.code() == 200) {
                    CheckBarcodeResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else
                    EventBus.getDefault().post(Cv.TIMEOUT);
            }

            @Override
            public void onFailure(Call<CheckBarcodeResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetSerialNumberReference() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getserialnumberreference(Preferences.getInstance(getApplicationContext()).getCid(), appUser.serial_ref_no).enqueue(new Callback<SerialNumberReferenceResponse>() {
            @Override
            public void onResponse(Call<SerialNumberReferenceResponse> call, Response<SerialNumberReferenceResponse> r) {
                if (r.code() == 200) {
                    SerialNumberReferenceResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else
                    EventBus.getDefault().post(Cv.TIMEOUT);
            }

            @Override
            public void onFailure(Call<SerialNumberReferenceResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetPdf() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getpdf(appUser.serial_voucher_type, appUser.serial_voucher_id).enqueue(new Callback<PdfResponse>() {
            @Override
            public void onResponse(Call<PdfResponse> call, Response<PdfResponse> r) {
                if (r.code() == 200) {
                    PdfResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else
                    EventBus.getDefault().post(Cv.TIMEOUT);
            }

            @Override
            public void onFailure(Call<PdfResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetStockTransfer() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getStockTransfer(Preferences.getInstance(getApplicationContext()).getCid(), appUser.sales_duration_spinner).enqueue(new Callback<GetStockTransferListResponse>() {
            @Override
            public void onResponse(Call<GetStockTransferListResponse> call, Response<GetStockTransferListResponse> r) {
                if (r.code() == 200) {
                    GetStockTransferListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetStockTransferListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCreateStockTransfer() {
        api.createStockTransfer(new RequestCreateStockTransfer(this), Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateStockTransferResponse>() {
            @Override
            public void onResponse(Call<CreateStockTransferResponse> call, Response<CreateStockTransferResponse> r) {
                if (r.code() == 200) {
                    CreateStockTransferResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateStockTransferResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCreateAuhorizationSettings() {
        api.createAuthorizationSettings(new RequestCreateAuthorizationSettings(this), Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateAuthorizationSettingsResponse>() {
            @Override
            public void onResponse(Call<CreateAuthorizationSettingsResponse> call, Response<CreateAuthorizationSettingsResponse> response) {
                if (response.code() == 200) {
                    CreateAuthorizationSettingsResponse body = response.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateAuthorizationSettingsResponse> call, Throwable t) {

                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleDeleteStockTransfer() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.deleteStockTransfer(appUser.delete_stock_transfer_id).enqueue(new Callback<DeleteStockTransferResponse>() {
            @Override
            public void onResponse(Call<DeleteStockTransferResponse> call, Response<DeleteStockTransferResponse> r) {
                if (r.code() == 200) {
                    DeleteStockTransferResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<DeleteStockTransferResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetStockTransferDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getStockTransferDetails(appUser.edit_stock_transfer_id).enqueue(new Callback<GetStockTransferDetailsResponse>() {
            @Override
            public void onResponse(Call<GetStockTransferDetailsResponse> call, Response<GetStockTransferDetailsResponse> r) {
                if (r.code() == 200) {
                    GetStockTransferDetailsResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetStockTransferDetailsResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetBalanceSheetPdf() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getBalanceSheetpdf(Preferences.getInstance(getApplicationContext()).getCid(), appUser.pdf_start_date, appUser.pdf_end_date).enqueue(new Callback<GetTransactionPdfResponse>() {
            @Override
            public void onResponse(Call<GetTransactionPdfResponse> call, Response<GetTransactionPdfResponse> r) {
                if (r.code() == 200) {
                    GetTransactionPdfResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetTransactionPdfResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetItemWiseReport() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getItemWiseReport(appUser.pdf_account_id, appUser.pdf_start_date, appUser.pdf_end_date).enqueue(new Callback<ItemWiseReportResponse>() {
            @Override
            public void onResponse(Call<ItemWiseReportResponse> call, Response<ItemWiseReportResponse> r) {
                if (r.code() == 200) {
                    ItemWiseReportResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<ItemWiseReportResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetSaleVouchersItem() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getSaleVouchersItem(Preferences.getInstance(getApplicationContext()).getCid(), appUser.start_date, appUser.end_date).enqueue(new Callback<GetSaleVouchersItem>() {
            @Override
            public void onResponse(Call<GetSaleVouchersItem> call, Response<GetSaleVouchersItem> r) {
                if (r.code() == 200) {
                    GetSaleVouchersItem body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetSaleVouchersItem> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetSaleVouchersItemDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getsaleVouchersItmDetails(Preferences.getInstance(getApplicationContext()).getCid(), appUser.sale_voucher_item_id, appUser.start_date, appUser.end_date).enqueue(new Callback<GetSaleVoucherListResponse>() {
            @Override
            public void onResponse(Call<GetSaleVoucherListResponse> call, Response<GetSaleVoucherListResponse> r) {
                if (r.code() == 200) {
                    GetSaleVoucherListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetSaleVoucherListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetPurchaseVouchersItem() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getPurchaseVouchersItem(Preferences.getInstance(getApplicationContext()).getCid(), appUser.start_date, appUser.end_date).enqueue(new Callback<GetSaleVouchersItem>() {
            @Override
            public void onResponse(Call<GetSaleVouchersItem> call, Response<GetSaleVouchersItem> r) {
                if (r.code() == 200) {
                    GetSaleVouchersItem body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetSaleVouchersItem> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetPurchaseVouchersItemDetails() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getPurchaseVouchersItmDetails(Preferences.getInstance(getApplicationContext()).getCid(), appUser.sale_voucher_item_id, appUser.start_date, appUser.end_date).enqueue(new Callback<GetPurchaseVoucherListResponse>() {
            @Override
            public void onResponse(Call<GetPurchaseVoucherListResponse> call, Response<GetPurchaseVoucherListResponse> r) {
                if (r.code() == 200) {
                    GetPurchaseVoucherListResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetPurchaseVoucherListResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetGst_1() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getgstr_1(Preferences.getInstance(getApplicationContext()).getCid(), appUser.pdf_start_date, appUser.pdf_end_date).enqueue(new Callback<CompanyReportResponse>() {
            @Override
            public void onResponse(Call<CompanyReportResponse> call, Response<CompanyReportResponse> r) {
                if (r.code() == 200) {
                    CompanyReportResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else
                    EventBus.getDefault().post(Cv.TIMEOUT);
            }

            @Override
            public void onFailure(Call<CompanyReportResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleGetGst_2() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getgstr_2(Preferences.getInstance(getApplicationContext()).getCid(), appUser.pdf_start_date, appUser.pdf_end_date).enqueue(new Callback<CompanyReportResponse>() {
            @Override
            public void onResponse(Call<CompanyReportResponse> call, Response<CompanyReportResponse> r) {
                if (r.code() == 200) {
                    CompanyReportResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else
                    EventBus.getDefault().post(Cv.TIMEOUT);
            }

            @Override
            public void onFailure(Call<CompanyReportResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleCompanyInvoice() {
        api.invoiceFormat(new RequestInvoiceFormat(this), Preferences.getInstance(getApplicationContext()).getCid()).enqueue(new Callback<CreateCompanyResponse>() {
            @Override
            public void onResponse(Call<CreateCompanyResponse> call, Response<CreateCompanyResponse> r) {
                if (r.code() == 200) {
                    CreateCompanyResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateCompanyResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });

    }

    private void handleGetVoucherSeries() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.getseries(Preferences.getInstance(getApplicationContext()).getCid(), "Sales").enqueue(new Callback<VoucherSeriesResponse>() {
            @Override
            public void onResponse(Call<VoucherSeriesResponse> call, Response<VoucherSeriesResponse> r) {
                if (r.code() == 200) {
                    VoucherSeriesResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<VoucherSeriesResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handlePosVoucher() {
        api.createPosVoucher(new RequestCreatePOSVoucher(this), Preferences.getInstance(this).getCid()).enqueue(new Callback<CreateSaleVoucherResponse>() {
            @Override
            public void onResponse(Call<CreateSaleVoucherResponse> call, Response<CreateSaleVoucherResponse> r) {
                if (r.code() == 200) {
                    CreateSaleVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<CreateSaleVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }

    private void handleUpdatePosVoucher() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.updatePosVoucher(new RequestCreatePOSVoucher(this), appUser.edit_sale_voucher_id).enqueue(new Callback<UpdateSaleVoucherResponse>() {
            @Override
            public void onResponse(Call<UpdateSaleVoucherResponse> call, Response<UpdateSaleVoucherResponse> r) {
                if (r.code() == 200) {
                    UpdateSaleVoucherResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<UpdateSaleVoucherResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }


   /* private void handleUpdateStockTransfer() {
        AppUser appUser = LocalRepositories.getAppUser(this);
        api.updateStockTransfer(new RequestCreateStockTransfer(this),*//*appUser.edit_stock_transfer_id*//*"2").enqueue(new Callback<UpdateStockTrasferResponse>() {
            @Override
            public void onResponse(Call<UpdateStockTrasferResponse> call, Response<UpdateStockTrasferResponse> r) {
                if (r.code() == 200) {
                    UpdateStockTrasferResponse body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<UpdateStockTrasferResponse> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }*/

}
