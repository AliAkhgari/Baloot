import React, { useEffect } from 'react';
import Authentication from '../api/authentication';

function Callback() {
    useEffect(() => {
        console.log('Executing effect');

        const fetchData = async () => {
            let search = window.location.search;
            let params = new URLSearchParams(search.split('?')[1]);

            console.log('params:', params);

            if (Authentication.getUserJWT() != null) {
                window.location.replace('/');
                return;
            }

            const req = { code: params.get('code') };
            console.log(req);

            try {
                const response = await Authentication.callback(req);
                console.log('Response:', response);

                let userJWT = response.headers.token;
                let username = response.headers.username;
                Authentication.setUser(userJWT, username);

                window.location.replace('/');
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
    }, []);

    return null;
}

export default Callback;
