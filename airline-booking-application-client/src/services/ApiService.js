import axios from "axios";
export default class ApiService {
  static BASE_URL = "http://localhost:8080/api/v1";
  static async get(url) {
    const response = await fetch(url);
    return response.json();
  }

  static async getAxios(url) {
    const response = await axios.get(url);
    return response.data;
  }

  static async post(url, data) {
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });
    return response.json();
  }

  static async postAxios(url, data) {
    const response = await axios.post(url, data);
    return response.data;
  }

  static async patch(url, data) {
    const response = await fetch(url, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });
    return response.json();
  }

  static async patchAxios(url, data) {
    const response = await axios.patch(url, data);
    return response.data;
  }

  static async put(url, data) {
    const response = await fetch(url, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });
    return response.json();
  }

  static async putAxios(url, data) {
    const response = await axios.put(url, data);
    return response.data;
  }

  static async delete(url) {
    const response = await fetch(url, {
      method: "DELETE",
    });
    return response.json();
  }

  static async deleteAxios(url) {
    const response = await axios.delete(url);
    return response.data;
  }

  static saveToken(token) {
    localStorage.setItem("token", token);
  }

  static getToken() {
    return localStorage.getItem("token");
  }

  static saveRole(roles) {
    localStorage.setItem("roles", JSON.stringify(roles));
  }

  static getRoles() {
    const roles = localStorage.getItem("roles");
    // return roles ? roles.split(",") : [];
    // return roles ? JSON.parse(roles) : [];
    return roles ? JSON.parse(roles) : null;
  }

  static hasRole(role) {
    const roles = this.getRoles();
    return roles ? roles.includes(role) : false;
  }

  static isAdmin() {
    return this.hasRole("ADMIN");
  }

  static isCustomer() {
    return this.hasRole("CUSTOMER");
  }

  static isPilot() {
    return this.hasRole("PILOT");
  }

  static isAuthenticated() {
    // return this.getToken() !== null;
    const token = this.getToken();
    // return token ? true : false;
    return !!token;
  }

  static getHeader() {
    const token = this.getToken();
    if (!token) {
      return {};
    }
    return {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };
  }

  static async register(body) {
    // const response = await fetch(
    //   "http://localhost:8080/api/v1/auth/register",
    //   {
    //     method: "POST",
    //     headers: {
    //       "Content-Type": "application/json",
    //     },
    //     body: JSON.stringify(body),
    //   }
    // );
    // return response.json();
    const response = await axios.post(`${this.BASE_URL}/auth/register`, body, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    return response.data;
  }

  static async login(body) {
    // const response = await fetch(
    //   "http://localhost:8080/api/v1/auth/login",
    //   {
    //     method: "POST",
    //     headers: {
    //       "Content-Type": "application/json",
    //     },
    //     body: JSON.stringify(body),
    //   }
    // );
    // return response.json();
    const response = await axios.post(`${this.BASE_URL}/auth/login`, body);
    return response.data;
  }

  static clearToken() {
    localStorage.removeItem("token");
    localStorage.removeItem("roles");
  }

  static async getAccountDetails() {
    // const response = await fetch(
    //   "http://localhost:8080/api/v1/auth/account-details",
    //   this.getHeader()
    // );
    // return response.json();
    const response = await axios.get(
      `${this.BASE_URL}/users/me`,
      this.getHeader()
    );
    return response.data;
  }

  static async updateAccountDetails(body) {
    // const response = await fetch(
    //   "http://localhost:8080/api/v1/auth/account-details",
    //   {
    //     method: "PUT",
    //     headers: {
    //       "Content-Type": "application/json",
    //       ...this.getHeader().headers,
    //     },
    //     body: JSON.stringify(body),
    //   }
    // );
    // return response.json();
    const response = await axios.put(
      `${this.BASE_URL}/users`,
      body,
      this.getHeader()
    );
    return response.data;
  }

  static async getAllPilots() {
    // const response = await fetch(
    //   "http://localhost:8080/api/v1/pilots",
    //   this.getHeader()
    // );
    // return response.json();
    const response = await axios.get(
      `${this.BASE_URL}/pilots`,
      this.getHeader()
    );
    return response.data;
  }


  /** Airport */

  static async getAllAirports() {
    // const response = await fetch(
    //   "http://localhost:8080/api/v1/airports",
    //   this.getHeader()
    // );
    // return response.json();
    const response = await axios.get(
      `${this.BASE_URL}/airports`,
      this.getHeader()
    );
    return response.data;
  }

  static async getAirport(id) {
    // const response = await fetch(
    //   `http://localhost:8080/api/v1/airports/${id}`,
    //   this.getHeader()
    // );
    // return response.json();
    const response = await axios.get(
      `${this.BASE_URL}/airports/${id}`,
      this.getHeader()
    );
    return response.data;
  }

  static async createAirport(body) {
    // const response = await fetch(
    //   "http://localhost:8080/api/v1/airports",
    //   {
    //     method: "POST",
    //     headers: {
    //       "Content-Type": "application/json",
    //       ...this.getHeader().headers,
    //     },
    //     body: JSON.stringify(body),
    //   }
    // );
    // return response.json();
    const response = await axios.post(
      `${this.BASE_URL}/airports`,
      body,
      this.getHeader()
    );
    return response.data;
  }

  static async updateAirport(body) {
    // const response = await fetch(
    //   `http://localhost:8080/api/v1/airports/${id}`,
    //   {
    //     method: "PUT",
    //     headers: {
    //       "Content-Type": "application/json",
    //       ...this.getHeader().headers,
    //     },
    //     body: JSON.stringify(body),
    //   }
    // );
    // return response.json();
    const response = await axios.put(
      `${this.BASE_URL}/airports/${body.id}`,
      body,
      this.getHeader()
    );
    return response.data;
  }

  static async deleteAirport(id) {
    // const response = await fetch(
    //   `http://localhost:8080/api/v1/airports/${id}`,
    //   {
    //     method: "DELETE",
    //     headers: {
    //       ...this.getHeader().headers,
    //     },
    //   }
    // );
    // return response.json();
    const response = await axios.delete(
      `${this.BASE_URL}/airports/${id}`,
      this.getHeader()
    );
    return response.data;
  }


  /** Booking */

  static async getAllBookings() {
    // const response = await fetch(
    //   "http://localhost:8080/api/v1/bookings",
    //   this.getHeader()
    // );
    // return response.json();
    const response = await axios.get(
      `${this.BASE_URL}/bookings`,
      this.getHeader()
    );
    return response.data;
  }

  static async getBooking(id) {
    // const response = await fetch(
    //   `http://localhost:8080/api/v1/bookings/${id}`,
    //   this.getHeader()
    // );
    // return response.json();
    const response = await axios.get(
      `${this.BASE_URL}/bookings/${id}`,
      this.getHeader()
    );
    return response.data;
  }

    static async getCurrentUserBookings() {
        // const resp = await fetch(`${this.BASE_URL}/bookings/me`, {
        //     headers: this.getHeader()
        // });
        // return resp.json();
        const resp = await axios.get(`${this.BASE_URL}/bookings/me`, {
            headers: this.getHeader()
        });
        return resp.data;
    }

  static async createBooking(body) {
    // const response = await fetch(
    //   "http://localhost:8080/api/v1/bookings",
    //   {
    //     method: "POST",
    //     headers: {
    //       "Content-Type": "application/json",
    //       ...this.getHeader().headers,
    //     },
    //     body: JSON.stringify(body),
    //   }
    // );
    // return response.json();
    const response = await axios.post(
      `${this.BASE_URL}/bookings`,
      body,
      this.getHeader()
    );
    return response.data;
  }

  static async updateBooking(id,status) {
    // const response = await fetch(
    //   `http://localhost:8080/api/v1/bookings/${id}`,
    //   {
    //     method: "PUT",
    //     headers: {
    //       "Content-Type": "application/json",
    //       ...this.getHeader().headers,
    //     },
    //     body: JSON.stringify(body),
    //   }
    // );
    // return response.json();
    const response = await axios.put(
      `${this.BASE_URL}/bookings/${id}`,
      {status},
      this.getHeader()
    );
    return response.data;
  }

  static async deleteBooking(id) {
    // const response = await fetch(
    //   `http://localhost:8080/api/v1/bookings/${id}`,
    //   {
    //     method: "DELETE",
    //     headers: {
    //       ...this.getHeader().headers,
    //     },
    //   }
    // );
    // return response.json();
    const response = await axios.delete(
      `${this.BASE_URL}/bookings/${id}`,
      this.getHeader()
    );
    return response.data;
  }


  /** Flights */

  static async searchFlightsByIataCode(departureIataCode, arrivalIataCode,departureDate) {
    // // const response = await fetch(
    // //   `http://localhost:8080/api/v1/flights/search/${departureIataCode}/${arrivalIataCode}`,
    // //   this.getHeader()
    // // );
    // // return response.json();
    // const response = await axios.get(
    //   `${this.BASE_URL}/flights/search/${departureIataCode}/${arrivalIataCode}`,
    //   this.getHeader()
    // );
    // return response.data;

    // const response = await axios.get(
    //   `${this.BASE_URL}/flights/search/${departureIataCode}/${arrivalIataCode}/${departureDate}`,
    //   this.getHeader()
    // );
    // return response.data;

    // const paramsOld={
    //   departureIataCode,
    //   arrivalIataCode,
    //   departureDate
    // }
    const params = new URLSearchParams();
    params.append("departureIataCode", departureIataCode);
    params.append("arrivalIataCode", arrivalIataCode);
    params.append("departureDate", departureDate);
    const response = await axios.get(
      `${this.BASE_URL}/flights/search?${params.toString()}`,
      this.getHeader()
    );
    return response.data;
  }

  static async searchFlights(body) {
    // const response = await fetch(
    //   "http://localhost:8080/api/v1/flights/search",
    //   {
    //     method: "POST",
    //     headers: {
    //       "Content-Type": "application/json",
    //       ...this.getHeader().headers,
    //     },
    //     body: JSON.stringify(body),
    //   }
    // );
    // return response.json();
    const response = await axios.post(
      `${this.BASE_URL}/flights/search`,
      body,
      this.getHeader()
    );
    return response.data;
  }

  static async getAllFlights() {
    // const response = await fetch(
    //   "http://localhost:8080/api/v1/flights",
    //   this.getHeader()
    // );
    // return response.json();
    const response = await axios.get(
      `${this.BASE_URL}/flights`,
      this.getHeader()
    );
    return response.data;
  }

  static async getFlight(id) {
    // const response = await fetch(
    //   `http://localhost:8080/api/v1/flights/${id}`,
    //   this.getHeader()
    // );
    // return response.json();
    const response = await axios.get(
      `${this.BASE_URL}/flights/${id}`,
      this.getHeader()
    );
    return response.data;
  }

  static async createFlight(body) {
    // const response = await fetch(
    //   "http://localhost:8080/api/v1/flights",
    //   {
    //     method: "POST",
    //     headers: {
    //       "Content-Type": "application/json",
    //       ...this.getHeader().headers,
    //     },
    //     body: JSON.stringify(body),
    //   }
    // );
    // return response.json();
    const response = await axios.post(
      `${this.BASE_URL}/flights`,
      body,
      this.getHeader()
    );
    return response.data;
  }

  static async updateFlight(id,body) {
    // const response = await fetch(
    //   `http://localhost:8080/api/v1/flights/${id}`,
    //   {
    //     method: "PUT",
    //     headers: {
    //       "Content-Type": "application/json",
    //       ...this.getHeader().headers,
    //     },
    //     body: JSON.stringify(body),
    //   }
    // );
    // return response.json();
    const response = await axios.put(
      `${this.BASE_URL}/flights/${id}`,
      body,
      this.getHeader()
    );
    return response.data;
  }

  static async deleteFlight(id) {
    // const response = await fetch(
    //   `http://localhost:8080/api/v1/flights/${id}`,
    //   {
    //     method: "DELETE",
    //     headers: {
    //       ...this.getHeader().headers,
    //     },
    //   }
    // );
    // return response.json();
    const response = await axios.delete(
      `${this.BASE_URL}/flights/${id}`,
      this.getHeader()
    );
    return response.data;
  }


  static async getAllCountries() {
    // const response = await fetch(
    //   "http://localhost:8080/api/v1/countries",
    //   this.getHeader()
    // );
    // return response.json();
    const response = await axios.get(
      `${this.BASE_URL}/flights/countries`,
      this.getHeader()
    );
    return response.data;
  }

  static async getAllCities() {
    // const response = await fetch(
    //   "http://localhost:8080/api/v1/cities",
    //   this.getHeader()
    // );
    // return response.json();
    const response = await axios.get(
      `${this.BASE_URL}/flights/cities`,
      this.getHeader()
    );
    return response.data;
  }


}
