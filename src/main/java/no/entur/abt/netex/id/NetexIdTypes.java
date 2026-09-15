package no.entur.abt.netex.id;

/*-
 * #%L
 * Netex utils
 * %%
 * Copyright (C) 2019 - 2021 Entur
 * %%
 * Licensed under the EUPL, Version 1.1 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 * 
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl5
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 * #L%
 */

import java.util.function.Function;
import java.util.function.Predicate;

import no.entur.abt.netex.id.predicate.NetexIdTypeValidatingNonThrowingPredicate;

/**
 * NeTEx id type constants, plus a non-throwing, type-validating predicate (and no-arg,
 * stream-filter-friendly overload) for each one. See the {@code isXxx} methods below.
 */
public class NetexIdTypes {
	public static final String AVAILABILITY_CONDITION = "AvailabilityCondition";
	public static final String TARIFF_ZONE = "TariffZone";
	public static final String FARE_ZONE = "FareZone";
	public static final String GROUP_OF_LINES = "GroupOfLines";
	public static final String GROUP_OF_TARIFF_ZONES = "GroupOfTariffZones";
	public static final String GROUP_TICKET = "GroupTicket";
	public static final String COMPANION_PROFILE = "CompanionProfile";
	public static final String COMMERCIAL_PROFILE = "CommercialProfile";
	public static final String AUTHORITY = "Authority";
	public static final String OPERATOR = "Operator";
	public static final String GENERAL_ORGANISATION = "GeneralOrganisation";
	public static final String CLASS_OF_USE = "ClassOfUse";
	public static final String CUSTOMER = "Customer";
	public static final String CUSTOMER_ACCOUNT = "CustomerAccount";
	public static final String CUSTOMER_PURCHASE_PACKAGE = "CustomerPurchasePackage";
	public static final String CUSTOMER_PURCHASE_PACKAGE_ELEMENT = "CustomerPurchasePackageElement";
	public static final String CUSTOMER_PURCHASE_PACKAGE_ELEMENT_ACCESS = "CustomerPurchasePackageElementAccess";
	public static final String CUSTOMER_PURCHASE_PARAMETER_ASSIGNMENT = "CustomerPurchaseParameterAssignment";
	public static final String FARE_CONTRACT = "FareContract";
	public static final String FARE_DAY_TYPE = "FareDayType";
	public static final String FARE_DEMAND_FACTOR = "FareDemandFactor";
	public static final String FARE_PRODUCT = "FareProduct";
	public static final String UIC_OPERATING_PERIOD = "UicOperatingPeriod";
	public static final String FREQUENCY_OF_USE = "FrequencyOfUse";
	public static final String PREASSIGNED_FARE_PRODUCT = "PreassignedFareProduct";
	public static final String SUPPLEMENT_PRODUCT = "SupplementProduct";
	public static final String LINE = "Line";
	public static final String ROUTE = "Route";
	public static final String FLEXIBLE_LINE = "FlexibleLine";
	public static final String USER_PROFILE = "UserProfile";
	public static final String SERVICE_JOURNEY = "ServiceJourney";
	public static final String DATED_SERVICE_JOURNEY = "DatedServiceJourney";
	public static final String JOURNEY_PATTERN = "JourneyPattern";
	public static final String NETWORK = "Network";
	public static final String SCHEDULED_STOP_POINT = "ScheduledStopPoint";
	public static final String STOP_POINT_IN_JOURNEY_PATTERN = "StopPointInJourneyPattern";
	public static final String STOP_PLACE = "StopPlace";
	public static final String QUAY = "Quay";
	public static final String DISTANCE_MATRIX_ELEMENT = "DistanceMatrixElement";
	public static final String DYNAMIC_DISTANCE_MATRIX_ELEMENT = "DynamicDistanceMatrixElement";
	public static final String SALE_DISCOUNT_RIGHT = "SaleDiscountRight";
	public static final String SALES_PACKAGE = "SalesPackage";
	public static final String SALES_PACKAGE_ELEMENT = "SalesPackageElement";
	public static final String SALES_TRANSACTION = "SalesTransaction";
	public static final String USAGE_DISCOUNT_RIGHT = "UsageDiscountRight";
	public static final String CAPPED_DISCOUNT_RIGHT = "CappedDiscountRight";
	public static final String AMOUNT_OF_PRICE_UNIT_PRODUCT = "AmountOfPriceUnitProduct";
	public static final String USAGE_VALIDITY_PERIOD = "UsageValidityPeriod";
	public static final String VALID_BETWEEN = "ValidBetween";
	public static final String VALID_DURING = "ValidDuring";
	public static final String DAY_TYPE = "DayType";
	public static final String THIRD_PARTY_PRODUCT = "ThirdPartyProduct";
	public static final String TIME_INTERVAL = "TimeInterval";
	public static final String TIMEBAND = "Timeband";
	public static final String TOPOGRAPHIC_PLACE = "TopographicPlace";
	public static final String TRANSFERABILITY = "Transferability";
	public static final String TYPE_OF_ACCESS_RIGHT_ASSIGNMENT = "TypeOfAccessRightAssignment";
	public static final String TYPE_OF_FARE_CONTRACT = "TypeOfFareContract";
	public static final String TYPE_OF_CUSTOMER_ACCOUNT = "TypeOfCustomerAccount";
	public static final String TYPE_OF_RESPONSIBILITY_ROLE = "TypeOfResponsibilityRole";
	public static final String TYPE_OF_TRAVEL_DOCUMENT = "TypeOfTravelDocument";
	public static final String TYPE_OF_CONCESSION = "TypeOfConcession";
	public static final String TYPE_OF_FARE_PRODUCT = "TypeOfFareProduct";
	public static final String TYPE_OF_USAGE_PARAMETER = "TypeOfUsageParameter";
	public static final String TYPE_OF_VALUE = "TypeOfValue";
	public static final String SALES_OFFER_PACKAGE = "SalesOfferPackage";
	public static final String SALES_OFFER_PACKAGE_ELEMENT = "SalesOfferPackageElement";
	public static final String SERVICE_LINK = "ServiceLink";
	public static final String SERVICE_LINK_IN_JOURNEY_PATTERN = "ServiceLinkInJourneyPattern";
	public static final String LINK_SEQUENCE_PROJECTION = "LinkSequenceProjection";
	public static final String VALIDABLE_ELEMENT = "ValidableElement";
	public static final String NOD_STOP_PLACE_MAPPING = "NodStopPlaceMapping";
	public static final String FARE_ZONE_MAPPING = "FareZoneMapping";
	public static final String TARIFF_CODE_MAPPING = "TariffCodeMapping";
	public static final String FARE_PRICE = "FarePrice";
	public static final String NOTICE_ASSIGNMENT = "NoticeAssignment";
	public static final String PRICE_UNIT = "PriceUnit";
	public static final String REVERTING = "Reverting";
	public static final String CODESPACE = "Codespace";
	public static final String DISTRIBUTION_CHANNEL = "DistributionChannel";
	public static final String FULFILMENT_METHOD = "FulfilmentMethod";
	public static final String PASSENGER_SEAT = "PassengerSeat";
	public static final String INTERCHANGING = "Interchanging";
	public static final String GEOGRAPHICAL_INTERVAL = "GeographicalInterval";
	public static final String FARE_STRUCTURE_ELEMENT = "FareStructureElement";
	public static final String LUGGAGE_ALLOWANCE = "LuggageAllowance";
	public static final String GEOGRAPHICAL_STRUCTURE_FACTOR = "GeographicalStructureFactor";
	public static final String CHARGING_MOMENT = "ChargingMoment";
	public static final String ENTITLEMENT_PRODUCT = "EntitlementProduct";
	public static final String ENTITLEMENT_REQUIRED = "EntitlementRequired";
	public static final String CHARGING_POLICY = "ChargingPolicy";
	public static final String ENTITLEMENT_GIVEN = "EntitlementGiven";
	public static final String EXCHANGING = "Exchanging";
	public static final String FARE_TABLE = "FareTable";
	public static final String GENERIC_PARAMETER_ASSIGNMENT = "GenericParameterAssignment";
	public static final String PENALTY_POLICY = "PenaltyPolicy";
	public static final String PURCHASE_WINDOW = "PurchaseWindow";
	public static final String REFUNDING = "Refunding";
	public static final String RESERVING = "Reserving";
	public static final String ROUND_TRIP = "RoundTrip";
	public static final String TARIFF = "Tariff";

	public static final String PARKING = "Parking";
	public static final String CANCELLING = "Cancelling";
	public static final String RESELLING = "Reselling";
	public static final String REPLACING = "Replacing";
	public static final String SUBSCRIBING = "Subscribing";
	public static final String ELIGIBILITY_CHANGE_POLICY = "EligibilityChangePolicy";
	public static final String ROUTING = "Routing";
	public static final String STEP_LIMIT = "StepLimit";
	public static final String SUSPENDING = "Suspending";
	public static final String MINIMUM_STAY = "MinimumStay";
	public static final String SALES_OFFER_PACKAGE_ENTITLEMENT_GIVEN = "SalesOfferPackageEntitlementGiven";
	public static final String SALES_OFFER_PACKAGE_ENTITLEMENT_REQUIRED = "SalesOfferPackageEntitlementRequired";

	public static final String FARE_SECTION = "FareSection";
	public static final String VERSION = "Version";
	public static final String SECURITY_POLICY = "SecurityPolicy";
	public static final String ONBOARD_VALIDITY = "OnboardValidity";
	public static final String VALIDITY_CONDITION = "ValidityCondition";
	public static final String OPERATING_DAY = "OperatingDay";
	public static final String RESPONSIBILITY_SET = "ResponsibilitySet";
	public static final String RESPONSIBILITY_ROLE_ASSIGNMENT = "ResponsibilityRoleAssignment";

	private static final DefaultNetexIdValidator VALIDATOR = DefaultNetexIdValidator.getInstance();
	private static final NetexIdValidatingParser PARSER = NetexIdValidatingParser.getInstance();

	/**
	 * Checks whether {@code id} is a valid NeTEx id and returns the type, without throwing on invalid
	 * input.
	 *
	 * @return the type of the id, or {@code null} if the id is invalid
	 */

	public static String getType(CharSequence id) {
		int valueIndex = PARSER.validateToValueIndex(id);
		if(valueIndex == -1) {
			return null;
		}

		return id.subSequence(DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH + 1, valueIndex - 1).toString();
	}

	/**
	 * Checks whether {@code id} is a valid NeTEx id of the given {@code type}, without throwing on invalid
	 * input.
	 */
	public static boolean isType(CharSequence id, String type) {
		if (id == null || type == null) {
			return false;
		}

		// minimum size is XXX:type:X
		if (id.length() < DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH + 1 + type.length() + 2) {
			// not valid or type too short
			return false;
		}

		if (id.charAt(DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH + 1 + type.length() ) != DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR) {
			// not valid or type too long
			return false;
		}

		// so type length matches
		boolean typeMatches;
		if (id instanceof String) {
			typeMatches = type.regionMatches(0, (String) id, 4, type.length());
		} else {
			typeMatches = true;
			for (int i = 0; i < type.length(); i++) {
				if (type.charAt(i) != id.charAt(4 + i)) {
					typeMatches = false;
					break;
				}
			}
		}

		return typeMatches && VALIDATOR.validate(id);
	}

	/**
	 * Non-throwing, type-validating predicates for every NeTEx id type declared above. Each method safely
	 * returns {@code false} (rather than throwing) for {@code null} or invalid input. For a
	 * {@link java.util.function.Predicate} instance usable in stream filters, see the matching no-arg
	 * method below, e.g. {@code ids.stream().filter(NetexIdTypes.isAuthority())}.
	 */

	public static boolean isAvailabilityCondition(CharSequence id) {
		return Predicates.AVAILABILITY_CONDITION_PREDICATE.test(id);
	}

	public static boolean isTariffZone(CharSequence id) {
		return Predicates.TARIFF_ZONE_PREDICATE.test(id);
	}

	public static boolean isFareZone(CharSequence id) {
		return Predicates.FARE_ZONE_PREDICATE.test(id);
	}

	public static boolean isGroupOfLines(CharSequence id) {
		return Predicates.GROUP_OF_LINES_PREDICATE.test(id);
	}

	public static boolean isGroupOfTariffZones(CharSequence id) {
		return Predicates.GROUP_OF_TARIFF_ZONES_PREDICATE.test(id);
	}

	public static boolean isGroupTicket(CharSequence id) {
		return Predicates.GROUP_TICKET_PREDICATE.test(id);
	}

	public static boolean isCompanionProfile(CharSequence id) {
		return Predicates.COMPANION_PROFILE_PREDICATE.test(id);
	}

	public static boolean isCommercialProfile(CharSequence id) {
		return Predicates.COMMERCIAL_PROFILE_PREDICATE.test(id);
	}

	public static boolean isAuthority(CharSequence id) {
		return Predicates.AUTHORITY_PREDICATE.test(id);
	}

	public static boolean isOperator(CharSequence id) {
		return Predicates.OPERATOR_PREDICATE.test(id);
	}

	public static boolean isGeneralOrganisation(CharSequence id) {
		return Predicates.GENERAL_ORGANISATION_PREDICATE.test(id);
	}

	public static boolean isClassOfUse(CharSequence id) {
		return Predicates.CLASS_OF_USE_PREDICATE.test(id);
	}

	public static boolean isCustomer(CharSequence id) {
		return Predicates.CUSTOMER_PREDICATE.test(id);
	}

	public static boolean isCustomerAccount(CharSequence id) {
		return Predicates.CUSTOMER_ACCOUNT_PREDICATE.test(id);
	}

	public static boolean isCustomerPurchasePackage(CharSequence id) {
		return Predicates.CUSTOMER_PURCHASE_PACKAGE_PREDICATE.test(id);
	}

	public static boolean isCustomerPurchasePackageElement(CharSequence id) {
		return Predicates.CUSTOMER_PURCHASE_PACKAGE_ELEMENT_PREDICATE.test(id);
	}

	public static boolean isCustomerPurchasePackageElementAccess(CharSequence id) {
		return Predicates.CUSTOMER_PURCHASE_PACKAGE_ELEMENT_ACCESS_PREDICATE.test(id);
	}

	public static boolean isCustomerPurchaseParameterAssignment(CharSequence id) {
		return Predicates.CUSTOMER_PURCHASE_PARAMETER_ASSIGNMENT_PREDICATE.test(id);
	}

	public static boolean isFareContract(CharSequence id) {
		return Predicates.FARE_CONTRACT_PREDICATE.test(id);
	}

	public static boolean isFareDayType(CharSequence id) {
		return Predicates.FARE_DAY_TYPE_PREDICATE.test(id);
	}

	public static boolean isFareDemandFactor(CharSequence id) {
		return Predicates.FARE_DEMAND_FACTOR_PREDICATE.test(id);
	}

	public static boolean isFareProduct(CharSequence id) {
		return Predicates.FARE_PRODUCT_PREDICATE.test(id);
	}

	public static boolean isUicOperatingPeriod(CharSequence id) {
		return Predicates.UIC_OPERATING_PERIOD_PREDICATE.test(id);
	}

	public static boolean isFrequencyOfUse(CharSequence id) {
		return Predicates.FREQUENCY_OF_USE_PREDICATE.test(id);
	}

	public static boolean isPreassignedFareProduct(CharSequence id) {
		return Predicates.PREASSIGNED_FARE_PRODUCT_PREDICATE.test(id);
	}

	public static boolean isSupplementProduct(CharSequence id) {
		return Predicates.SUPPLEMENT_PRODUCT_PREDICATE.test(id);
	}

	public static boolean isLine(CharSequence id) {
		return Predicates.LINE_PREDICATE.test(id);
	}

	public static boolean isRoute(CharSequence id) {
		return Predicates.ROUTE_PREDICATE.test(id);
	}

	public static boolean isFlexibleLine(CharSequence id) {
		return Predicates.FLEXIBLE_LINE_PREDICATE.test(id);
	}

	public static boolean isUserProfile(CharSequence id) {
		return Predicates.USER_PROFILE_PREDICATE.test(id);
	}

	public static boolean isServiceJourney(CharSequence id) {
		return Predicates.SERVICE_JOURNEY_PREDICATE.test(id);
	}

	public static boolean isDatedServiceJourney(CharSequence id) {
		return Predicates.DATED_SERVICE_JOURNEY_PREDICATE.test(id);
	}

	public static boolean isJourneyPattern(CharSequence id) {
		return Predicates.JOURNEY_PATTERN_PREDICATE.test(id);
	}

	public static boolean isNetwork(CharSequence id) {
		return Predicates.NETWORK_PREDICATE.test(id);
	}

	public static boolean isScheduledStopPoint(CharSequence id) {
		return Predicates.SCHEDULED_STOP_POINT_PREDICATE.test(id);
	}

	public static boolean isStopPointInJourneyPattern(CharSequence id) {
		return Predicates.STOP_POINT_IN_JOURNEY_PATTERN_PREDICATE.test(id);
	}

	public static boolean isStopPlace(CharSequence id) {
		return Predicates.STOP_PLACE_PREDICATE.test(id);
	}

	public static boolean isQuay(CharSequence id) {
		return Predicates.QUAY_PREDICATE.test(id);
	}

	public static boolean isDistanceMatrixElement(CharSequence id) {
		return Predicates.DISTANCE_MATRIX_ELEMENT_PREDICATE.test(id);
	}

	public static boolean isDynamicDistanceMatrixElement(CharSequence id) {
		return Predicates.DYNAMIC_DISTANCE_MATRIX_ELEMENT_PREDICATE.test(id);
	}

	public static boolean isSaleDiscountRight(CharSequence id) {
		return Predicates.SALE_DISCOUNT_RIGHT_PREDICATE.test(id);
	}

	public static boolean isSalesPackage(CharSequence id) {
		return Predicates.SALES_PACKAGE_PREDICATE.test(id);
	}

	public static boolean isSalesPackageElement(CharSequence id) {
		return Predicates.SALES_PACKAGE_ELEMENT_PREDICATE.test(id);
	}

	public static boolean isSalesTransaction(CharSequence id) {
		return Predicates.SALES_TRANSACTION_PREDICATE.test(id);
	}

	public static boolean isUsageDiscountRight(CharSequence id) {
		return Predicates.USAGE_DISCOUNT_RIGHT_PREDICATE.test(id);
	}

	public static boolean isCappedDiscountRight(CharSequence id) {
		return Predicates.CAPPED_DISCOUNT_RIGHT_PREDICATE.test(id);
	}

	public static boolean isAmountOfPriceUnitProduct(CharSequence id) {
		return Predicates.AMOUNT_OF_PRICE_UNIT_PRODUCT_PREDICATE.test(id);
	}

	public static boolean isUsageValidityPeriod(CharSequence id) {
		return Predicates.USAGE_VALIDITY_PERIOD_PREDICATE.test(id);
	}

	public static boolean isValidBetween(CharSequence id) {
		return Predicates.VALID_BETWEEN_PREDICATE.test(id);
	}

	public static boolean isValidDuring(CharSequence id) {
		return Predicates.VALID_DURING_PREDICATE.test(id);
	}

	public static boolean isDayType(CharSequence id) {
		return Predicates.DAY_TYPE_PREDICATE.test(id);
	}

	public static boolean isThirdPartyProduct(CharSequence id) {
		return Predicates.THIRD_PARTY_PRODUCT_PREDICATE.test(id);
	}

	public static boolean isTimeInterval(CharSequence id) {
		return Predicates.TIME_INTERVAL_PREDICATE.test(id);
	}

	public static boolean isTimeband(CharSequence id) {
		return Predicates.TIMEBAND_PREDICATE.test(id);
	}

	public static boolean isTopographicPlace(CharSequence id) {
		return Predicates.TOPOGRAPHIC_PLACE_PREDICATE.test(id);
	}

	public static boolean isTransferability(CharSequence id) {
		return Predicates.TRANSFERABILITY_PREDICATE.test(id);
	}

	public static boolean isTypeOfAccessRightAssignment(CharSequence id) {
		return Predicates.TYPE_OF_ACCESS_RIGHT_ASSIGNMENT_PREDICATE.test(id);
	}

	public static boolean isTypeOfFareContract(CharSequence id) {
		return Predicates.TYPE_OF_FARE_CONTRACT_PREDICATE.test(id);
	}

	public static boolean isTypeOfCustomerAccount(CharSequence id) {
		return Predicates.TYPE_OF_CUSTOMER_ACCOUNT_PREDICATE.test(id);
	}

	public static boolean isTypeOfResponsibilityRole(CharSequence id) {
		return Predicates.TYPE_OF_RESPONSIBILITY_ROLE_PREDICATE.test(id);
	}

	public static boolean isTypeOfTravelDocument(CharSequence id) {
		return Predicates.TYPE_OF_TRAVEL_DOCUMENT_PREDICATE.test(id);
	}

	public static boolean isTypeOfConcession(CharSequence id) {
		return Predicates.TYPE_OF_CONCESSION_PREDICATE.test(id);
	}

	public static boolean isTypeOfFareProduct(CharSequence id) {
		return Predicates.TYPE_OF_FARE_PRODUCT_PREDICATE.test(id);
	}

	public static boolean isTypeOfUsageParameter(CharSequence id) {
		return Predicates.TYPE_OF_USAGE_PARAMETER_PREDICATE.test(id);
	}

	public static boolean isTypeOfValue(CharSequence id) {
		return Predicates.TYPE_OF_VALUE_PREDICATE.test(id);
	}

	public static boolean isSalesOfferPackage(CharSequence id) {
		return Predicates.SALES_OFFER_PACKAGE_PREDICATE.test(id);
	}

	public static boolean isSalesOfferPackageElement(CharSequence id) {
		return Predicates.SALES_OFFER_PACKAGE_ELEMENT_PREDICATE.test(id);
	}

	public static boolean isServiceLink(CharSequence id) {
		return Predicates.SERVICE_LINK_PREDICATE.test(id);
	}

	public static boolean isServiceLinkInJourneyPattern(CharSequence id) {
		return Predicates.SERVICE_LINK_IN_JOURNEY_PATTERN_PREDICATE.test(id);
	}

	public static boolean isLinkSequenceProjection(CharSequence id) {
		return Predicates.LINK_SEQUENCE_PROJECTION_PREDICATE.test(id);
	}

	public static boolean isValidableElement(CharSequence id) {
		return Predicates.VALIDABLE_ELEMENT_PREDICATE.test(id);
	}

	public static boolean isNodStopPlaceMapping(CharSequence id) {
		return Predicates.NOD_STOP_PLACE_MAPPING_PREDICATE.test(id);
	}

	public static boolean isFareZoneMapping(CharSequence id) {
		return Predicates.FARE_ZONE_MAPPING_PREDICATE.test(id);
	}

	public static boolean isTariffCodeMapping(CharSequence id) {
		return Predicates.TARIFF_CODE_MAPPING_PREDICATE.test(id);
	}

	public static boolean isFarePrice(CharSequence id) {
		return Predicates.FARE_PRICE_PREDICATE.test(id);
	}

	public static boolean isNoticeAssignment(CharSequence id) {
		return Predicates.NOTICE_ASSIGNMENT_PREDICATE.test(id);
	}

	public static boolean isPriceUnit(CharSequence id) {
		return Predicates.PRICE_UNIT_PREDICATE.test(id);
	}

	public static boolean isReverting(CharSequence id) {
		return Predicates.REVERTING_PREDICATE.test(id);
	}

	public static boolean isCodespace(CharSequence id) {
		return Predicates.CODESPACE_PREDICATE.test(id);
	}

	public static boolean isDistributionChannel(CharSequence id) {
		return Predicates.DISTRIBUTION_CHANNEL_PREDICATE.test(id);
	}

	public static boolean isFulfilmentMethod(CharSequence id) {
		return Predicates.FULFILMENT_METHOD_PREDICATE.test(id);
	}

	public static boolean isPassengerSeat(CharSequence id) {
		return Predicates.PASSENGER_SEAT_PREDICATE.test(id);
	}

	public static boolean isInterchanging(CharSequence id) {
		return Predicates.INTERCHANGING_PREDICATE.test(id);
	}

	public static boolean isGeographicalInterval(CharSequence id) {
		return Predicates.GEOGRAPHICAL_INTERVAL_PREDICATE.test(id);
	}

	public static boolean isFareStructureElement(CharSequence id) {
		return Predicates.FARE_STRUCTURE_ELEMENT_PREDICATE.test(id);
	}

	public static boolean isLuggageAllowance(CharSequence id) {
		return Predicates.LUGGAGE_ALLOWANCE_PREDICATE.test(id);
	}

	public static boolean isGeographicalStructureFactor(CharSequence id) {
		return Predicates.GEOGRAPHICAL_STRUCTURE_FACTOR_PREDICATE.test(id);
	}

	public static boolean isChargingMoment(CharSequence id) {
		return Predicates.CHARGING_MOMENT_PREDICATE.test(id);
	}

	public static boolean isEntitlementProduct(CharSequence id) {
		return Predicates.ENTITLEMENT_PRODUCT_PREDICATE.test(id);
	}

	public static boolean isEntitlementRequired(CharSequence id) {
		return Predicates.ENTITLEMENT_REQUIRED_PREDICATE.test(id);
	}

	public static boolean isChargingPolicy(CharSequence id) {
		return Predicates.CHARGING_POLICY_PREDICATE.test(id);
	}

	public static boolean isEntitlementGiven(CharSequence id) {
		return Predicates.ENTITLEMENT_GIVEN_PREDICATE.test(id);
	}

	public static boolean isExchanging(CharSequence id) {
		return Predicates.EXCHANGING_PREDICATE.test(id);
	}

	public static boolean isFareTable(CharSequence id) {
		return Predicates.FARE_TABLE_PREDICATE.test(id);
	}

	public static boolean isGenericParameterAssignment(CharSequence id) {
		return Predicates.GENERIC_PARAMETER_ASSIGNMENT_PREDICATE.test(id);
	}

	public static boolean isPenaltyPolicy(CharSequence id) {
		return Predicates.PENALTY_POLICY_PREDICATE.test(id);
	}

	public static boolean isPurchaseWindow(CharSequence id) {
		return Predicates.PURCHASE_WINDOW_PREDICATE.test(id);
	}

	public static boolean isRefunding(CharSequence id) {
		return Predicates.REFUNDING_PREDICATE.test(id);
	}

	public static boolean isReserving(CharSequence id) {
		return Predicates.RESERVING_PREDICATE.test(id);
	}

	public static boolean isRoundTrip(CharSequence id) {
		return Predicates.ROUND_TRIP_PREDICATE.test(id);
	}

	public static boolean isTariff(CharSequence id) {
		return Predicates.TARIFF_PREDICATE.test(id);
	}

	public static boolean isParking(CharSequence id) {
		return Predicates.PARKING_PREDICATE.test(id);
	}

	public static boolean isCancelling(CharSequence id) {
		return Predicates.CANCELLING_PREDICATE.test(id);
	}

	public static boolean isReselling(CharSequence id) {
		return Predicates.RESELLING_PREDICATE.test(id);
	}

	public static boolean isReplacing(CharSequence id) {
		return Predicates.REPLACING_PREDICATE.test(id);
	}

	public static boolean isSubscribing(CharSequence id) {
		return Predicates.SUBSCRIBING_PREDICATE.test(id);
	}

	public static boolean isEligibilityChangePolicy(CharSequence id) {
		return Predicates.ELIGIBILITY_CHANGE_POLICY_PREDICATE.test(id);
	}

	public static boolean isRouting(CharSequence id) {
		return Predicates.ROUTING_PREDICATE.test(id);
	}

	public static boolean isStepLimit(CharSequence id) {
		return Predicates.STEP_LIMIT_PREDICATE.test(id);
	}

	public static boolean isSuspending(CharSequence id) {
		return Predicates.SUSPENDING_PREDICATE.test(id);
	}

	public static boolean isMinimumStay(CharSequence id) {
		return Predicates.MINIMUM_STAY_PREDICATE.test(id);
	}

	public static boolean isSalesOfferPackageEntitlementGiven(CharSequence id) {
		return Predicates.SALES_OFFER_PACKAGE_ENTITLEMENT_GIVEN_PREDICATE.test(id);
	}

	public static boolean isSalesOfferPackageEntitlementRequired(CharSequence id) {
		return Predicates.SALES_OFFER_PACKAGE_ENTITLEMENT_REQUIRED_PREDICATE.test(id);
	}

	public static boolean isFareSection(CharSequence id) {
		return Predicates.FARE_SECTION_PREDICATE.test(id);
	}

	public static boolean isVersion(CharSequence id) {
		return Predicates.VERSION_PREDICATE.test(id);
	}

	public static boolean isSecurityPolicy(CharSequence id) {
		return Predicates.SECURITY_POLICY_PREDICATE.test(id);
	}

	public static boolean isOnboardValidity(CharSequence id) {
		return Predicates.ONBOARD_VALIDITY_PREDICATE.test(id);
	}

	public static boolean isValidityCondition(CharSequence id) {
		return Predicates.VALIDITY_CONDITION_PREDICATE.test(id);
	}

	public static boolean isOperatingDay(CharSequence id) {
		return Predicates.OPERATING_DAY_PREDICATE.test(id);
	}

	public static boolean isResponsibilitySet(CharSequence id) {
		return Predicates.RESPONSIBILITY_SET_PREDICATE.test(id);
	}

	public static boolean isResponsibilityRoleAssignment(CharSequence id) {
		return Predicates.RESPONSIBILITY_ROLE_ASSIGNMENT_PREDICATE.test(id);
	}

	/**
	 * {@link java.util.function.Predicate} instances for every NeTEx id type, one no-arg method per type,
	 * for convenient use in stream filters, e.g. {@code ids.stream().filter(NetexIdTypes.isAuthority())}.
	 */

	public static Predicate<CharSequence> isAvailabilityCondition() {
		return Predicates.AVAILABILITY_CONDITION_PREDICATE;
	}

	public static Predicate<CharSequence> isTariffZone() {
		return Predicates.TARIFF_ZONE_PREDICATE;
	}

	public static Predicate<CharSequence> isFareZone() {
		return Predicates.FARE_ZONE_PREDICATE;
	}

	public static Predicate<CharSequence> isGroupOfLines() {
		return Predicates.GROUP_OF_LINES_PREDICATE;
	}

	public static Predicate<CharSequence> isGroupOfTariffZones() {
		return Predicates.GROUP_OF_TARIFF_ZONES_PREDICATE;
	}

	public static Predicate<CharSequence> isGroupTicket() {
		return Predicates.GROUP_TICKET_PREDICATE;
	}

	public static Predicate<CharSequence> isCompanionProfile() {
		return Predicates.COMPANION_PROFILE_PREDICATE;
	}

	public static Predicate<CharSequence> isCommercialProfile() {
		return Predicates.COMMERCIAL_PROFILE_PREDICATE;
	}

	public static Predicate<CharSequence> isAuthority() {
		return Predicates.AUTHORITY_PREDICATE;
	}

	public static Predicate<CharSequence> isOperator() {
		return Predicates.OPERATOR_PREDICATE;
	}

	public static Predicate<CharSequence> isGeneralOrganisation() {
		return Predicates.GENERAL_ORGANISATION_PREDICATE;
	}

	public static Predicate<CharSequence> isClassOfUse() {
		return Predicates.CLASS_OF_USE_PREDICATE;
	}

	public static Predicate<CharSequence> isCustomer() {
		return Predicates.CUSTOMER_PREDICATE;
	}

	public static Predicate<CharSequence> isCustomerAccount() {
		return Predicates.CUSTOMER_ACCOUNT_PREDICATE;
	}

	public static Predicate<CharSequence> isCustomerPurchasePackage() {
		return Predicates.CUSTOMER_PURCHASE_PACKAGE_PREDICATE;
	}

	public static Predicate<CharSequence> isCustomerPurchasePackageElement() {
		return Predicates.CUSTOMER_PURCHASE_PACKAGE_ELEMENT_PREDICATE;
	}

	public static Predicate<CharSequence> isCustomerPurchasePackageElementAccess() {
		return Predicates.CUSTOMER_PURCHASE_PACKAGE_ELEMENT_ACCESS_PREDICATE;
	}

	public static Predicate<CharSequence> isCustomerPurchaseParameterAssignment() {
		return Predicates.CUSTOMER_PURCHASE_PARAMETER_ASSIGNMENT_PREDICATE;
	}

	public static Predicate<CharSequence> isFareContract() {
		return Predicates.FARE_CONTRACT_PREDICATE;
	}

	public static Predicate<CharSequence> isFareDayType() {
		return Predicates.FARE_DAY_TYPE_PREDICATE;
	}

	public static Predicate<CharSequence> isFareDemandFactor() {
		return Predicates.FARE_DEMAND_FACTOR_PREDICATE;
	}

	public static Predicate<CharSequence> isFareProduct() {
		return Predicates.FARE_PRODUCT_PREDICATE;
	}

	public static Predicate<CharSequence> isUicOperatingPeriod() {
		return Predicates.UIC_OPERATING_PERIOD_PREDICATE;
	}

	public static Predicate<CharSequence> isFrequencyOfUse() {
		return Predicates.FREQUENCY_OF_USE_PREDICATE;
	}

	public static Predicate<CharSequence> isPreassignedFareProduct() {
		return Predicates.PREASSIGNED_FARE_PRODUCT_PREDICATE;
	}

	public static Predicate<CharSequence> isSupplementProduct() {
		return Predicates.SUPPLEMENT_PRODUCT_PREDICATE;
	}

	public static Predicate<CharSequence> isLine() {
		return Predicates.LINE_PREDICATE;
	}

	public static Predicate<CharSequence> isRoute() {
		return Predicates.ROUTE_PREDICATE;
	}

	public static Predicate<CharSequence> isFlexibleLine() {
		return Predicates.FLEXIBLE_LINE_PREDICATE;
	}

	public static Predicate<CharSequence> isUserProfile() {
		return Predicates.USER_PROFILE_PREDICATE;
	}

	public static Predicate<CharSequence> isServiceJourney() {
		return Predicates.SERVICE_JOURNEY_PREDICATE;
	}

	public static Predicate<CharSequence> isDatedServiceJourney() {
		return Predicates.DATED_SERVICE_JOURNEY_PREDICATE;
	}

	public static Predicate<CharSequence> isJourneyPattern() {
		return Predicates.JOURNEY_PATTERN_PREDICATE;
	}

	public static Predicate<CharSequence> isNetwork() {
		return Predicates.NETWORK_PREDICATE;
	}

	public static Predicate<CharSequence> isScheduledStopPoint() {
		return Predicates.SCHEDULED_STOP_POINT_PREDICATE;
	}

	public static Predicate<CharSequence> isStopPointInJourneyPattern() {
		return Predicates.STOP_POINT_IN_JOURNEY_PATTERN_PREDICATE;
	}

	public static Predicate<CharSequence> isStopPlace() {
		return Predicates.STOP_PLACE_PREDICATE;
	}

	public static Predicate<CharSequence> isQuay() {
		return Predicates.QUAY_PREDICATE;
	}

	public static Predicate<CharSequence> isDistanceMatrixElement() {
		return Predicates.DISTANCE_MATRIX_ELEMENT_PREDICATE;
	}

	public static Predicate<CharSequence> isDynamicDistanceMatrixElement() {
		return Predicates.DYNAMIC_DISTANCE_MATRIX_ELEMENT_PREDICATE;
	}

	public static Predicate<CharSequence> isSaleDiscountRight() {
		return Predicates.SALE_DISCOUNT_RIGHT_PREDICATE;
	}

	public static Predicate<CharSequence> isSalesPackage() {
		return Predicates.SALES_PACKAGE_PREDICATE;
	}

	public static Predicate<CharSequence> isSalesPackageElement() {
		return Predicates.SALES_PACKAGE_ELEMENT_PREDICATE;
	}

	public static Predicate<CharSequence> isSalesTransaction() {
		return Predicates.SALES_TRANSACTION_PREDICATE;
	}

	public static Predicate<CharSequence> isUsageDiscountRight() {
		return Predicates.USAGE_DISCOUNT_RIGHT_PREDICATE;
	}

	public static Predicate<CharSequence> isCappedDiscountRight() {
		return Predicates.CAPPED_DISCOUNT_RIGHT_PREDICATE;
	}

	public static Predicate<CharSequence> isAmountOfPriceUnitProduct() {
		return Predicates.AMOUNT_OF_PRICE_UNIT_PRODUCT_PREDICATE;
	}

	public static Predicate<CharSequence> isUsageValidityPeriod() {
		return Predicates.USAGE_VALIDITY_PERIOD_PREDICATE;
	}

	public static Predicate<CharSequence> isValidBetween() {
		return Predicates.VALID_BETWEEN_PREDICATE;
	}

	public static Predicate<CharSequence> isValidDuring() {
		return Predicates.VALID_DURING_PREDICATE;
	}

	public static Predicate<CharSequence> isDayType() {
		return Predicates.DAY_TYPE_PREDICATE;
	}

	public static Predicate<CharSequence> isThirdPartyProduct() {
		return Predicates.THIRD_PARTY_PRODUCT_PREDICATE;
	}

	public static Predicate<CharSequence> isTimeInterval() {
		return Predicates.TIME_INTERVAL_PREDICATE;
	}

	public static Predicate<CharSequence> isTimeband() {
		return Predicates.TIMEBAND_PREDICATE;
	}

	public static Predicate<CharSequence> isTopographicPlace() {
		return Predicates.TOPOGRAPHIC_PLACE_PREDICATE;
	}

	public static Predicate<CharSequence> isTransferability() {
		return Predicates.TRANSFERABILITY_PREDICATE;
	}

	public static Predicate<CharSequence> isTypeOfAccessRightAssignment() {
		return Predicates.TYPE_OF_ACCESS_RIGHT_ASSIGNMENT_PREDICATE;
	}

	public static Predicate<CharSequence> isTypeOfFareContract() {
		return Predicates.TYPE_OF_FARE_CONTRACT_PREDICATE;
	}

	public static Predicate<CharSequence> isTypeOfCustomerAccount() {
		return Predicates.TYPE_OF_CUSTOMER_ACCOUNT_PREDICATE;
	}

	public static Predicate<CharSequence> isTypeOfResponsibilityRole() {
		return Predicates.TYPE_OF_RESPONSIBILITY_ROLE_PREDICATE;
	}

	public static Predicate<CharSequence> isTypeOfTravelDocument() {
		return Predicates.TYPE_OF_TRAVEL_DOCUMENT_PREDICATE;
	}

	public static Predicate<CharSequence> isTypeOfConcession() {
		return Predicates.TYPE_OF_CONCESSION_PREDICATE;
	}

	public static Predicate<CharSequence> isTypeOfFareProduct() {
		return Predicates.TYPE_OF_FARE_PRODUCT_PREDICATE;
	}

	public static Predicate<CharSequence> isTypeOfUsageParameter() {
		return Predicates.TYPE_OF_USAGE_PARAMETER_PREDICATE;
	}

	public static Predicate<CharSequence> isTypeOfValue() {
		return Predicates.TYPE_OF_VALUE_PREDICATE;
	}

	public static Predicate<CharSequence> isSalesOfferPackage() {
		return Predicates.SALES_OFFER_PACKAGE_PREDICATE;
	}

	public static Predicate<CharSequence> isSalesOfferPackageElement() {
		return Predicates.SALES_OFFER_PACKAGE_ELEMENT_PREDICATE;
	}

	public static Predicate<CharSequence> isServiceLink() {
		return Predicates.SERVICE_LINK_PREDICATE;
	}

	public static Predicate<CharSequence> isServiceLinkInJourneyPattern() {
		return Predicates.SERVICE_LINK_IN_JOURNEY_PATTERN_PREDICATE;
	}

	public static Predicate<CharSequence> isLinkSequenceProjection() {
		return Predicates.LINK_SEQUENCE_PROJECTION_PREDICATE;
	}

	public static Predicate<CharSequence> isValidableElement() {
		return Predicates.VALIDABLE_ELEMENT_PREDICATE;
	}

	public static Predicate<CharSequence> isNodStopPlaceMapping() {
		return Predicates.NOD_STOP_PLACE_MAPPING_PREDICATE;
	}

	public static Predicate<CharSequence> isFareZoneMapping() {
		return Predicates.FARE_ZONE_MAPPING_PREDICATE;
	}

	public static Predicate<CharSequence> isTariffCodeMapping() {
		return Predicates.TARIFF_CODE_MAPPING_PREDICATE;
	}

	public static Predicate<CharSequence> isFarePrice() {
		return Predicates.FARE_PRICE_PREDICATE;
	}

	public static Predicate<CharSequence> isNoticeAssignment() {
		return Predicates.NOTICE_ASSIGNMENT_PREDICATE;
	}

	public static Predicate<CharSequence> isPriceUnit() {
		return Predicates.PRICE_UNIT_PREDICATE;
	}

	public static Predicate<CharSequence> isReverting() {
		return Predicates.REVERTING_PREDICATE;
	}

	public static Predicate<CharSequence> isCodespace() {
		return Predicates.CODESPACE_PREDICATE;
	}

	public static Predicate<CharSequence> isDistributionChannel() {
		return Predicates.DISTRIBUTION_CHANNEL_PREDICATE;
	}

	public static Predicate<CharSequence> isFulfilmentMethod() {
		return Predicates.FULFILMENT_METHOD_PREDICATE;
	}

	public static Predicate<CharSequence> isPassengerSeat() {
		return Predicates.PASSENGER_SEAT_PREDICATE;
	}

	public static Predicate<CharSequence> isInterchanging() {
		return Predicates.INTERCHANGING_PREDICATE;
	}

	public static Predicate<CharSequence> isGeographicalInterval() {
		return Predicates.GEOGRAPHICAL_INTERVAL_PREDICATE;
	}

	public static Predicate<CharSequence> isFareStructureElement() {
		return Predicates.FARE_STRUCTURE_ELEMENT_PREDICATE;
	}

	public static Predicate<CharSequence> isLuggageAllowance() {
		return Predicates.LUGGAGE_ALLOWANCE_PREDICATE;
	}

	public static Predicate<CharSequence> isGeographicalStructureFactor() {
		return Predicates.GEOGRAPHICAL_STRUCTURE_FACTOR_PREDICATE;
	}

	public static Predicate<CharSequence> isChargingMoment() {
		return Predicates.CHARGING_MOMENT_PREDICATE;
	}

	public static Predicate<CharSequence> isEntitlementProduct() {
		return Predicates.ENTITLEMENT_PRODUCT_PREDICATE;
	}

	public static Predicate<CharSequence> isEntitlementRequired() {
		return Predicates.ENTITLEMENT_REQUIRED_PREDICATE;
	}

	public static Predicate<CharSequence> isChargingPolicy() {
		return Predicates.CHARGING_POLICY_PREDICATE;
	}

	public static Predicate<CharSequence> isEntitlementGiven() {
		return Predicates.ENTITLEMENT_GIVEN_PREDICATE;
	}

	public static Predicate<CharSequence> isExchanging() {
		return Predicates.EXCHANGING_PREDICATE;
	}

	public static Predicate<CharSequence> isFareTable() {
		return Predicates.FARE_TABLE_PREDICATE;
	}

	public static Predicate<CharSequence> isGenericParameterAssignment() {
		return Predicates.GENERIC_PARAMETER_ASSIGNMENT_PREDICATE;
	}

	public static Predicate<CharSequence> isPenaltyPolicy() {
		return Predicates.PENALTY_POLICY_PREDICATE;
	}

	public static Predicate<CharSequence> isPurchaseWindow() {
		return Predicates.PURCHASE_WINDOW_PREDICATE;
	}

	public static Predicate<CharSequence> isRefunding() {
		return Predicates.REFUNDING_PREDICATE;
	}

	public static Predicate<CharSequence> isReserving() {
		return Predicates.RESERVING_PREDICATE;
	}

	public static Predicate<CharSequence> isRoundTrip() {
		return Predicates.ROUND_TRIP_PREDICATE;
	}

	public static Predicate<CharSequence> isTariff() {
		return Predicates.TARIFF_PREDICATE;
	}

	public static Predicate<CharSequence> isParking() {
		return Predicates.PARKING_PREDICATE;
	}

	public static Predicate<CharSequence> isCancelling() {
		return Predicates.CANCELLING_PREDICATE;
	}

	public static Predicate<CharSequence> isReselling() {
		return Predicates.RESELLING_PREDICATE;
	}

	public static Predicate<CharSequence> isReplacing() {
		return Predicates.REPLACING_PREDICATE;
	}

	public static Predicate<CharSequence> isSubscribing() {
		return Predicates.SUBSCRIBING_PREDICATE;
	}

	public static Predicate<CharSequence> isEligibilityChangePolicy() {
		return Predicates.ELIGIBILITY_CHANGE_POLICY_PREDICATE;
	}

	public static Predicate<CharSequence> isRouting() {
		return Predicates.ROUTING_PREDICATE;
	}

	public static Predicate<CharSequence> isStepLimit() {
		return Predicates.STEP_LIMIT_PREDICATE;
	}

	public static Predicate<CharSequence> isSuspending() {
		return Predicates.SUSPENDING_PREDICATE;
	}

	public static Predicate<CharSequence> isMinimumStay() {
		return Predicates.MINIMUM_STAY_PREDICATE;
	}

	public static Predicate<CharSequence> isSalesOfferPackageEntitlementGiven() {
		return Predicates.SALES_OFFER_PACKAGE_ENTITLEMENT_GIVEN_PREDICATE;
	}

	public static Predicate<CharSequence> isSalesOfferPackageEntitlementRequired() {
		return Predicates.SALES_OFFER_PACKAGE_ENTITLEMENT_REQUIRED_PREDICATE;
	}

	public static Predicate<CharSequence> isFareSection() {
		return Predicates.FARE_SECTION_PREDICATE;
	}

	public static Predicate<CharSequence> isVersion() {
		return Predicates.VERSION_PREDICATE;
	}

	public static Predicate<CharSequence> isSecurityPolicy() {
		return Predicates.SECURITY_POLICY_PREDICATE;
	}

	public static Predicate<CharSequence> isOnboardValidity() {
		return Predicates.ONBOARD_VALIDITY_PREDICATE;
	}

	public static Predicate<CharSequence> isValidityCondition() {
		return Predicates.VALIDITY_CONDITION_PREDICATE;
	}

	public static Predicate<CharSequence> isOperatingDay() {
		return Predicates.OPERATING_DAY_PREDICATE;
	}

	public static Predicate<CharSequence> isResponsibilitySet() {
		return Predicates.RESPONSIBILITY_SET_PREDICATE;
	}

	public static Predicate<CharSequence> isResponsibilityRoleAssignment() {
		return Predicates.RESPONSIBILITY_ROLE_ASSIGNMENT_PREDICATE;
	}

	/**
	 * Backing {@link no.entur.abt.netex.id.predicate.NetexIdTypeValidatingNonThrowingPredicate} instances for
	 * every NeTEx id type, shared by the {@code isXxx(CharSequence)} and no-arg {@code isXxx()} methods above.
	 */
	public static class Predicates {

		private static final NetexIdTypeValidatingNonThrowingPredicate AVAILABILITY_CONDITION_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(AVAILABILITY_CONDITION);
		private static final NetexIdTypeValidatingNonThrowingPredicate TARIFF_ZONE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TARIFF_ZONE);
		private static final NetexIdTypeValidatingNonThrowingPredicate FARE_ZONE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(FARE_ZONE);
		private static final NetexIdTypeValidatingNonThrowingPredicate GROUP_OF_LINES_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(GROUP_OF_LINES);
		private static final NetexIdTypeValidatingNonThrowingPredicate GROUP_OF_TARIFF_ZONES_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(GROUP_OF_TARIFF_ZONES);
		private static final NetexIdTypeValidatingNonThrowingPredicate GROUP_TICKET_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(GROUP_TICKET);
		private static final NetexIdTypeValidatingNonThrowingPredicate COMPANION_PROFILE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(COMPANION_PROFILE);
		private static final NetexIdTypeValidatingNonThrowingPredicate COMMERCIAL_PROFILE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(COMMERCIAL_PROFILE);
		private static final NetexIdTypeValidatingNonThrowingPredicate AUTHORITY_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(AUTHORITY);
		private static final NetexIdTypeValidatingNonThrowingPredicate OPERATOR_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(OPERATOR);
		private static final NetexIdTypeValidatingNonThrowingPredicate GENERAL_ORGANISATION_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(GENERAL_ORGANISATION);
		private static final NetexIdTypeValidatingNonThrowingPredicate CLASS_OF_USE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(CLASS_OF_USE);
		private static final NetexIdTypeValidatingNonThrowingPredicate CUSTOMER_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(CUSTOMER);
		private static final NetexIdTypeValidatingNonThrowingPredicate CUSTOMER_ACCOUNT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(CUSTOMER_ACCOUNT);
		private static final NetexIdTypeValidatingNonThrowingPredicate CUSTOMER_PURCHASE_PACKAGE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(CUSTOMER_PURCHASE_PACKAGE);
		private static final NetexIdTypeValidatingNonThrowingPredicate CUSTOMER_PURCHASE_PACKAGE_ELEMENT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(CUSTOMER_PURCHASE_PACKAGE_ELEMENT);
		private static final NetexIdTypeValidatingNonThrowingPredicate CUSTOMER_PURCHASE_PACKAGE_ELEMENT_ACCESS_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(CUSTOMER_PURCHASE_PACKAGE_ELEMENT_ACCESS);
		private static final NetexIdTypeValidatingNonThrowingPredicate CUSTOMER_PURCHASE_PARAMETER_ASSIGNMENT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(CUSTOMER_PURCHASE_PARAMETER_ASSIGNMENT);
		private static final NetexIdTypeValidatingNonThrowingPredicate FARE_CONTRACT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(FARE_CONTRACT);
		private static final NetexIdTypeValidatingNonThrowingPredicate FARE_DAY_TYPE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(FARE_DAY_TYPE);
		private static final NetexIdTypeValidatingNonThrowingPredicate FARE_DEMAND_FACTOR_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(FARE_DEMAND_FACTOR);
		private static final NetexIdTypeValidatingNonThrowingPredicate FARE_PRODUCT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(FARE_PRODUCT);
		private static final NetexIdTypeValidatingNonThrowingPredicate UIC_OPERATING_PERIOD_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(UIC_OPERATING_PERIOD);
		private static final NetexIdTypeValidatingNonThrowingPredicate FREQUENCY_OF_USE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(FREQUENCY_OF_USE);
		private static final NetexIdTypeValidatingNonThrowingPredicate PREASSIGNED_FARE_PRODUCT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(PREASSIGNED_FARE_PRODUCT);
		private static final NetexIdTypeValidatingNonThrowingPredicate SUPPLEMENT_PRODUCT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SUPPLEMENT_PRODUCT);
		private static final NetexIdTypeValidatingNonThrowingPredicate LINE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(LINE);
		private static final NetexIdTypeValidatingNonThrowingPredicate ROUTE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(ROUTE);
		private static final NetexIdTypeValidatingNonThrowingPredicate FLEXIBLE_LINE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(FLEXIBLE_LINE);
		private static final NetexIdTypeValidatingNonThrowingPredicate USER_PROFILE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(USER_PROFILE);
		private static final NetexIdTypeValidatingNonThrowingPredicate SERVICE_JOURNEY_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SERVICE_JOURNEY);
		private static final NetexIdTypeValidatingNonThrowingPredicate DATED_SERVICE_JOURNEY_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(DATED_SERVICE_JOURNEY);
		private static final NetexIdTypeValidatingNonThrowingPredicate JOURNEY_PATTERN_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(JOURNEY_PATTERN);
		private static final NetexIdTypeValidatingNonThrowingPredicate NETWORK_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(NETWORK);
		private static final NetexIdTypeValidatingNonThrowingPredicate SCHEDULED_STOP_POINT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SCHEDULED_STOP_POINT);
		private static final NetexIdTypeValidatingNonThrowingPredicate STOP_POINT_IN_JOURNEY_PATTERN_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(STOP_POINT_IN_JOURNEY_PATTERN);
		private static final NetexIdTypeValidatingNonThrowingPredicate STOP_PLACE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(STOP_PLACE);
		private static final NetexIdTypeValidatingNonThrowingPredicate QUAY_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(QUAY);
		private static final NetexIdTypeValidatingNonThrowingPredicate DISTANCE_MATRIX_ELEMENT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(DISTANCE_MATRIX_ELEMENT);
		private static final NetexIdTypeValidatingNonThrowingPredicate DYNAMIC_DISTANCE_MATRIX_ELEMENT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(DYNAMIC_DISTANCE_MATRIX_ELEMENT);
		private static final NetexIdTypeValidatingNonThrowingPredicate SALE_DISCOUNT_RIGHT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SALE_DISCOUNT_RIGHT);
		private static final NetexIdTypeValidatingNonThrowingPredicate SALES_PACKAGE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SALES_PACKAGE);
		private static final NetexIdTypeValidatingNonThrowingPredicate SALES_PACKAGE_ELEMENT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SALES_PACKAGE_ELEMENT);
		private static final NetexIdTypeValidatingNonThrowingPredicate SALES_TRANSACTION_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SALES_TRANSACTION);
		private static final NetexIdTypeValidatingNonThrowingPredicate USAGE_DISCOUNT_RIGHT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(USAGE_DISCOUNT_RIGHT);
		private static final NetexIdTypeValidatingNonThrowingPredicate CAPPED_DISCOUNT_RIGHT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(CAPPED_DISCOUNT_RIGHT);
		private static final NetexIdTypeValidatingNonThrowingPredicate AMOUNT_OF_PRICE_UNIT_PRODUCT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(AMOUNT_OF_PRICE_UNIT_PRODUCT);
		private static final NetexIdTypeValidatingNonThrowingPredicate USAGE_VALIDITY_PERIOD_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(USAGE_VALIDITY_PERIOD);
		private static final NetexIdTypeValidatingNonThrowingPredicate VALID_BETWEEN_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(VALID_BETWEEN);
		private static final NetexIdTypeValidatingNonThrowingPredicate VALID_DURING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(VALID_DURING);
		private static final NetexIdTypeValidatingNonThrowingPredicate DAY_TYPE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(DAY_TYPE);
		private static final NetexIdTypeValidatingNonThrowingPredicate THIRD_PARTY_PRODUCT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(THIRD_PARTY_PRODUCT);
		private static final NetexIdTypeValidatingNonThrowingPredicate TIME_INTERVAL_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TIME_INTERVAL);
		private static final NetexIdTypeValidatingNonThrowingPredicate TIMEBAND_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TIMEBAND);
		private static final NetexIdTypeValidatingNonThrowingPredicate TOPOGRAPHIC_PLACE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TOPOGRAPHIC_PLACE);
		private static final NetexIdTypeValidatingNonThrowingPredicate TRANSFERABILITY_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TRANSFERABILITY);
		private static final NetexIdTypeValidatingNonThrowingPredicate TYPE_OF_ACCESS_RIGHT_ASSIGNMENT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TYPE_OF_ACCESS_RIGHT_ASSIGNMENT);
		private static final NetexIdTypeValidatingNonThrowingPredicate TYPE_OF_FARE_CONTRACT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TYPE_OF_FARE_CONTRACT);
		private static final NetexIdTypeValidatingNonThrowingPredicate TYPE_OF_CUSTOMER_ACCOUNT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TYPE_OF_CUSTOMER_ACCOUNT);
		private static final NetexIdTypeValidatingNonThrowingPredicate TYPE_OF_RESPONSIBILITY_ROLE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TYPE_OF_RESPONSIBILITY_ROLE);
		private static final NetexIdTypeValidatingNonThrowingPredicate TYPE_OF_TRAVEL_DOCUMENT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TYPE_OF_TRAVEL_DOCUMENT);
		private static final NetexIdTypeValidatingNonThrowingPredicate TYPE_OF_CONCESSION_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TYPE_OF_CONCESSION);
		private static final NetexIdTypeValidatingNonThrowingPredicate TYPE_OF_FARE_PRODUCT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TYPE_OF_FARE_PRODUCT);
		private static final NetexIdTypeValidatingNonThrowingPredicate TYPE_OF_USAGE_PARAMETER_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TYPE_OF_USAGE_PARAMETER);
		private static final NetexIdTypeValidatingNonThrowingPredicate TYPE_OF_VALUE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TYPE_OF_VALUE);
		private static final NetexIdTypeValidatingNonThrowingPredicate SALES_OFFER_PACKAGE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SALES_OFFER_PACKAGE);
		private static final NetexIdTypeValidatingNonThrowingPredicate SALES_OFFER_PACKAGE_ELEMENT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SALES_OFFER_PACKAGE_ELEMENT);
		private static final NetexIdTypeValidatingNonThrowingPredicate SERVICE_LINK_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SERVICE_LINK);
		private static final NetexIdTypeValidatingNonThrowingPredicate SERVICE_LINK_IN_JOURNEY_PATTERN_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SERVICE_LINK_IN_JOURNEY_PATTERN);
		private static final NetexIdTypeValidatingNonThrowingPredicate LINK_SEQUENCE_PROJECTION_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(LINK_SEQUENCE_PROJECTION);
		private static final NetexIdTypeValidatingNonThrowingPredicate VALIDABLE_ELEMENT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(VALIDABLE_ELEMENT);
		private static final NetexIdTypeValidatingNonThrowingPredicate NOD_STOP_PLACE_MAPPING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(NOD_STOP_PLACE_MAPPING);
		private static final NetexIdTypeValidatingNonThrowingPredicate FARE_ZONE_MAPPING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(FARE_ZONE_MAPPING);
		private static final NetexIdTypeValidatingNonThrowingPredicate TARIFF_CODE_MAPPING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TARIFF_CODE_MAPPING);
		private static final NetexIdTypeValidatingNonThrowingPredicate FARE_PRICE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(FARE_PRICE);
		private static final NetexIdTypeValidatingNonThrowingPredicate NOTICE_ASSIGNMENT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(NOTICE_ASSIGNMENT);
		private static final NetexIdTypeValidatingNonThrowingPredicate PRICE_UNIT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(PRICE_UNIT);
		private static final NetexIdTypeValidatingNonThrowingPredicate REVERTING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(REVERTING);
		private static final NetexIdTypeValidatingNonThrowingPredicate CODESPACE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(CODESPACE);
		private static final NetexIdTypeValidatingNonThrowingPredicate DISTRIBUTION_CHANNEL_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(DISTRIBUTION_CHANNEL);
		private static final NetexIdTypeValidatingNonThrowingPredicate FULFILMENT_METHOD_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(FULFILMENT_METHOD);
		private static final NetexIdTypeValidatingNonThrowingPredicate PASSENGER_SEAT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(PASSENGER_SEAT);
		private static final NetexIdTypeValidatingNonThrowingPredicate INTERCHANGING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(INTERCHANGING);
		private static final NetexIdTypeValidatingNonThrowingPredicate GEOGRAPHICAL_INTERVAL_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(GEOGRAPHICAL_INTERVAL);
		private static final NetexIdTypeValidatingNonThrowingPredicate FARE_STRUCTURE_ELEMENT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(FARE_STRUCTURE_ELEMENT);
		private static final NetexIdTypeValidatingNonThrowingPredicate LUGGAGE_ALLOWANCE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(LUGGAGE_ALLOWANCE);
		private static final NetexIdTypeValidatingNonThrowingPredicate GEOGRAPHICAL_STRUCTURE_FACTOR_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(GEOGRAPHICAL_STRUCTURE_FACTOR);
		private static final NetexIdTypeValidatingNonThrowingPredicate CHARGING_MOMENT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(CHARGING_MOMENT);
		private static final NetexIdTypeValidatingNonThrowingPredicate ENTITLEMENT_PRODUCT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(ENTITLEMENT_PRODUCT);
		private static final NetexIdTypeValidatingNonThrowingPredicate ENTITLEMENT_REQUIRED_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(ENTITLEMENT_REQUIRED);
		private static final NetexIdTypeValidatingNonThrowingPredicate CHARGING_POLICY_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(CHARGING_POLICY);
		private static final NetexIdTypeValidatingNonThrowingPredicate ENTITLEMENT_GIVEN_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(ENTITLEMENT_GIVEN);
		private static final NetexIdTypeValidatingNonThrowingPredicate EXCHANGING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(EXCHANGING);
		private static final NetexIdTypeValidatingNonThrowingPredicate FARE_TABLE_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(FARE_TABLE);
		private static final NetexIdTypeValidatingNonThrowingPredicate GENERIC_PARAMETER_ASSIGNMENT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(GENERIC_PARAMETER_ASSIGNMENT);
		private static final NetexIdTypeValidatingNonThrowingPredicate PENALTY_POLICY_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(PENALTY_POLICY);
		private static final NetexIdTypeValidatingNonThrowingPredicate PURCHASE_WINDOW_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(PURCHASE_WINDOW);
		private static final NetexIdTypeValidatingNonThrowingPredicate REFUNDING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(REFUNDING);
		private static final NetexIdTypeValidatingNonThrowingPredicate RESERVING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(RESERVING);
		private static final NetexIdTypeValidatingNonThrowingPredicate ROUND_TRIP_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(ROUND_TRIP);
		private static final NetexIdTypeValidatingNonThrowingPredicate TARIFF_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(TARIFF);
		private static final NetexIdTypeValidatingNonThrowingPredicate PARKING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(PARKING);
		private static final NetexIdTypeValidatingNonThrowingPredicate CANCELLING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(CANCELLING);
		private static final NetexIdTypeValidatingNonThrowingPredicate RESELLING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(RESELLING);
		private static final NetexIdTypeValidatingNonThrowingPredicate REPLACING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(REPLACING);
		private static final NetexIdTypeValidatingNonThrowingPredicate SUBSCRIBING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SUBSCRIBING);
		private static final NetexIdTypeValidatingNonThrowingPredicate ELIGIBILITY_CHANGE_POLICY_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(ELIGIBILITY_CHANGE_POLICY);
		private static final NetexIdTypeValidatingNonThrowingPredicate ROUTING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(ROUTING);
		private static final NetexIdTypeValidatingNonThrowingPredicate STEP_LIMIT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(STEP_LIMIT);
		private static final NetexIdTypeValidatingNonThrowingPredicate SUSPENDING_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SUSPENDING);
		private static final NetexIdTypeValidatingNonThrowingPredicate MINIMUM_STAY_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(MINIMUM_STAY);
		private static final NetexIdTypeValidatingNonThrowingPredicate SALES_OFFER_PACKAGE_ENTITLEMENT_GIVEN_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SALES_OFFER_PACKAGE_ENTITLEMENT_GIVEN);
		private static final NetexIdTypeValidatingNonThrowingPredicate SALES_OFFER_PACKAGE_ENTITLEMENT_REQUIRED_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SALES_OFFER_PACKAGE_ENTITLEMENT_REQUIRED);
		private static final NetexIdTypeValidatingNonThrowingPredicate FARE_SECTION_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(FARE_SECTION);
		private static final NetexIdTypeValidatingNonThrowingPredicate VERSION_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(VERSION);
		private static final NetexIdTypeValidatingNonThrowingPredicate SECURITY_POLICY_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(SECURITY_POLICY);
		private static final NetexIdTypeValidatingNonThrowingPredicate ONBOARD_VALIDITY_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(ONBOARD_VALIDITY);
		private static final NetexIdTypeValidatingNonThrowingPredicate VALIDITY_CONDITION_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(VALIDITY_CONDITION);
		private static final NetexIdTypeValidatingNonThrowingPredicate OPERATING_DAY_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(OPERATING_DAY);
		private static final NetexIdTypeValidatingNonThrowingPredicate RESPONSIBILITY_SET_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(RESPONSIBILITY_SET);
		private static final NetexIdTypeValidatingNonThrowingPredicate RESPONSIBILITY_ROLE_ASSIGNMENT_PREDICATE = new NetexIdTypeValidatingNonThrowingPredicate(RESPONSIBILITY_ROLE_ASSIGNMENT);

		private Predicates() {
		}
	}

	private NetexIdTypes() {
	}

}
