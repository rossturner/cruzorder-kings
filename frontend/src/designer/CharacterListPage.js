import {Button, Container, Header, Placeholder, Table} from "semantic-ui-react";
import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";


const CharacterListPage = ({loggedInPlayer}) => {

    const [loading, setLoading] = useState(true);
    const [characters, setCharacters] = useState([]);
    // let history = useHistory();

    useEffect(() => {
        axios.get('/api/characters')
            .then((response) => {
                setCharacters(response.data);
                setLoading(false);
            })
            .catch((error) => {
                console.error('Error retrieving characters', error);
            })
    }, []);

    return (
        <React.Fragment>
            <Container>
                <Header as='h2'>
                    {loggedInPlayer.isAdmin && 'All characters'}
                    {!loggedInPlayer.isAdmin && loggedInPlayer.discordUsername+"'s Characters"}
                </Header>
                {loading &&
                    <Placeholder>
                        <Placeholder.Line />
                        <Placeholder.Line />
                        <Placeholder.Line />
                        <Placeholder.Line />
                        <Placeholder.Line />
                    </Placeholder>
                }

                {!loading &&
                <React.Fragment>

                    {characters.length > 0 &&
                        <Table celled>
                            <Table.Header>
                                <Table.Row>
                                    <Table.HeaderCell>Player</Table.HeaderCell>
                                    <Table.HeaderCell>Character Name</Table.HeaderCell>
                                    <Table.HeaderCell>Dynasty Name</Table.HeaderCell>
                                    <Table.HeaderCell>Territory</Table.HeaderCell>
                                    <Table.HeaderCell>Edit</Table.HeaderCell>
                                </Table.Row>
                            </Table.Header>

                            <Table.Body>

                            </Table.Body>
                        </Table>
                    }

                    {characters.length === 0 && <p>You have not yet created any characters.</p>}

                    <Button as={Link} to={'/designer/new'} primary>Create new</Button>
                </React.Fragment>
                }
            </Container>
        </React.Fragment>
    );
};


export default CharacterListPage;