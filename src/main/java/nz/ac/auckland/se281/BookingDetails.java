package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;

public class BookingDetails extends Venue {

  private String bookingDate;
  private String partyDate;
  private String email;
  private String attendees;
  private String reference;
  private VenueDetails venue;
  private CateringType cateringType;
  private boolean musicService = false;
  private FloralType floralType;

  public BookingDetails(String[] options, String ref, String bookingDate, VenueDetails venue) {
    this.code = options[0];
    this.partyDate = options[1];
    this.email = options[2];
    this.attendees = options[3];
    this.reference = ref;
    this.bookingDate = bookingDate;
    this.venue = venue;
  }

  public void checkCapacity(String capcacity) {

    // Reduce repeated calculations by storing it in tempAnswer
    tempAnswer = Integer.parseInt(capcacity) / 4;

    if (tempAnswer > Integer.parseInt(this.attendees)) {
      MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(
          attendees, Integer.toString(tempAnswer), capcacity);
      this.attendees = Integer.toString(tempAnswer);
    } else if (Integer.parseInt(this.attendees) > Integer.parseInt(capcacity)) {
      MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(attendees, capcacity, capcacity);
      this.attendees = capcacity;
    }
  }

  public void setCateringType(CateringType cateringType) {
    this.cateringType = cateringType;
    MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage(
        "Catering (" + cateringType.getName() + ")", this.reference);
  }

  public void musicServices() {
    musicService = true;
    MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage("Music", this.reference);
  }

  public void setFloralType(FloralType floralType) {
    this.floralType = floralType;
    MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage(
        "Floral (" + floralType.getName() + ")", this.reference);
  }

  public void printInvoiceContent() {
    // tempAnswer calculates the cost as it runs printInvoiceContent
    // Resetting tempAnswer to prevent unwanted errors
    tempAnswer = 0;

    tempAnswer = Integer.parseInt(getHireFee());
    MessageCli.INVOICE_CONTENT_VENUE_FEE.printMessage(getHireFee());

    if (cateringType != null) {
      tempAnswer += cateringType.getCostPerPerson() * Integer.parseInt(attendees);
      MessageCli.INVOICE_CONTENT_CATERING_ENTRY.printMessage(
          cateringType.getName(), Integer.toString(tempAnswer - Integer.parseInt(getHireFee())));
    }

    if (this.musicService == true) {
      tempAnswer += 500;
      MessageCli.INVOICE_CONTENT_MUSIC_ENTRY.printMessage("500");
    }

    if (floralType != null) {
      tempAnswer += floralType.getCost();
      MessageCli.INVOICE_CONTENT_FLORAL_ENTRY.printMessage(
          floralType.getName(), Integer.toString(floralType.getCost()));
    }
  }

  public String getTotalAmount() {
    // getTotalAmount is ran after printInvoiceContent
    // therefore the value of tempAnswer does not change
    return Integer.toString(tempAnswer);
  }

  @Override
  public String getName() {
    return venue.getName();
  }

  @Override
  public String getHireFee() {
    return venue.getHireFee();
  }

  @Override
  public String getCapacity() {
    return venue.getCapacity();
  }

  public String getAttendees() {
    return attendees;
  }

  public String getEmail() {
    return email;
  }

  public String getPartyDate() {
    return partyDate;
  }

  public String getBookingDate() {
    return bookingDate;
  }

  public String getReference() {
    return reference;
  }
}
