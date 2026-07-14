import http from 'k6/http';
import { check } from 'k6';

export const options = {
    vus: 10,
    iterations: 10,
};

export default function () {

    const url = 'http://localhost:8080/api/bookings';

    const payload = JSON.stringify({
        seatId: 1
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
            'X-User-Id': `${__VU}`
        }
    };

    const res = http.post(url, payload, params);

    console.log(`VU ${__VU} -> ${res.status}`);

    check(res, {
        'status is 201 or 409 or 500': (r) =>
            r.status === 201 ||
            r.status === 409 ||
            r.status === 500,
    });
}