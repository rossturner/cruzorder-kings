import './App.css';
import TopLevelMenu from "./header/TopLevelMenu";
import React, {useEffect, useState} from "react";
import jwt from "jsonwebtoken";
import {Route, Switch, withRouter} from "react-router-dom";
import axios from 'axios';
import Footer from "./header/Footer";
import AdminPage from "./admin/AdminPage";
import HomePage from "./HomePage";
import CharacterDesignerPage from "./designer/CharacterDesignerPage";

const App = ({history}) => {

    const [loading, setLoading] = useState(true);
    const [loggedInPlayer, setLoggedInPlayer] = useState();

    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        let jsonWebToken = urlParams.get('token');
        if (jsonWebToken) {
            window.localStorage.setItem('token', jsonWebToken);
        } else {
            jsonWebToken = window.localStorage.getItem('token');
            // TODO check for expiry and trigger refresh of token here
        }

        if (jsonWebToken) {
            axios.defaults.headers.common['Authorization'] = jsonWebToken;
            const decoded = jwt.decode(jsonWebToken);
            setLoggedInPlayer({
                discordUsername: decoded.username,
                discordId: decoded.sub,
                discordAvatar: decoded.avatar,
                isAdmin: decoded.is_admin
            });
        }

        setLoading(false);
    }, []);

    const logout = () => {
        window.localStorage.clear();
        setLoggedInPlayer(undefined);
        history.push('/');
    }

    if (loading) {
        return <div>Loading...</div>
    }
    return (
        <div>
            <TopLevelMenu loggedInPlayer={loggedInPlayer}/>

            <Switch>
                <Route exact path="/">
                    <HomePage />
                </Route>
                <Route exact path="/designer">
                    <CharacterDesignerPage loggedInPlayer={loggedInPlayer} />
                </Route>
                <Route path="/admin">
                    <AdminPage />
                </Route>
            </Switch>

            <Footer onLogout={logout} loggedInPlayer={loggedInPlayer} />
        </div>
    );
}

export default withRouter(App);
