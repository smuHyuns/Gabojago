import { defineStore } from 'pinia';

export const useAddTripStore = defineStore('trip', {
  state: () => ({
    country: localStorage.getItem('country') || null,
    headcount: localStorage.getItem('headcount') || 1,
    start_period: localStorage.getItem('start_period') || null,
    end_period: localStorage.getItem('end_period') || null,
    description: localStorage.getItem('description') || null,
    tripId: localStorage.getItem('tripId') || null,
  }),
  actions: {
    setCountry(country) {
      this.country = country;
      localStorage.setItem('country', country);
    },
    setHeadcount(headcount) {
      this.headcount = headcount;
      localStorage.setItem('headcount', headcount);
    },
    setStartPeriod(startPeriod) {
      this.start_period = startPeriod;
      localStorage.setItem('start_period', startPeriod);
    },
    setEndPeriod(endPeriod) {
      this.end_period = endPeriod;
      localStorage.setItem('end_period', endPeriod);
    },
    setDescription(description) {
      this.description = description;
      localStorage.setItem('description', description);
    },
    setTripId(tripId) {
      this.tripId = tripId;
      localStorage.setItem('tripId', tripId);
    },
    clearTripData() {
      this.country = null;
      this.headcount = null;
      this.start_period = null;
      this.end_period = null;
      this.description = null;
      this.tripId = null;
      localStorage.removeItem('country');
      localStorage.removeItem('headcount');
      localStorage.removeItem('start_period');
      localStorage.removeItem('end_period');
      localStorage.removeItem('description');
      localStorage.removeItem('tripId');
    },
  },
});
