import React, {useEffect, useState} from 'react';
import './App.scss';
import {Button, Dropdown, Form, Modal, ToggleButton, ToggleButtonGroup} from "react-bootstrap";

function App() {

    const [joke, setJoke] = useState({joke: null, setup: null, delivery: null});
    const [modal, setModal] = useState(false);
    const [showPun, setShowPun] = useState(false);

    const [categories, setCategories] = useState([]);
    const [flags, setFlags] = useState([]);
    const [jokeTypes, setJokeTypes] = useState([]);
    const [lang, setLang] = useState("Select language");
    const [searchString, setSearchString] = useState("");
    const [amount, setAmount] = useState(0);

    const randomJoke = () => {
        return fetch("http://localhost:8888/getOneJoke")
            .then((response) => response.json())
            .then((data) => setJoke(data))
            .then(() => setShowPun(false));
    }
    const newJokes = () => {
        const params: any = {
            categories: categories.join(','),
            flags: flags.join(','),
            jokeTypes: jokeTypes.join(','),
            language: lang,
            searchString: searchString,
            amount: amount
        }

        const paramString = new URLSearchParams(params).toString();
        fetch(`http://localhost:8888/loadNewJokes?${paramString}`, {
            method: "post",

        })
    }

    useEffect(() => {
        randomJoke();
    }, [])

    const getRandomJoke = () => {
        randomJoke();
    };
    const getNewJokes = () => {
        newJokes();
        setModal(false)
    };

    const handleCategoriesChanged = (value: any) => {
        setCategories(value)
    }
    const handleFlagsChanged = (value: any) => {
        setFlags(value)
    }
    const handleJokeTypesChanged = (value: any) => {
        setJokeTypes(value)
    }
    const handleSearchStringChanged = (value: any) => {
        setSearchString(value.target.value)
    }
    const handleAmountChanged = (value: any) => {
        if (value.target.value >= 1 && value.target.value <= 10) {
            setAmount(value.target.value)
        }
    }

    return (
        <div className="App">
            <div className={"joke-container"}>
                <>
                    {joke?.joke &&
                        <p className={"joke"}>
                            {joke.joke}
                        </p>
                    }
                </>
                <>
                    {joke?.setup && joke?.delivery &&
                        <div>
                            <p className={"joke"}>
                                {joke.setup}
                            </p>
                            <br/>
                            <>
                                {!showPun &&
                                    <Button variant={"secondary"} className={"reveal-joke"}
                                            onClick={() => setShowPun(true)}>Reveal delivery</Button>
                                }
                                {showPun &&
                                    <p className={"pun"}>
                                        {joke.delivery}
                                    </p>
                                }
                            </>
                        </div>
                    }
                </>
            </div>
            <Button className={"fetch-button"} onClick={() => setModal(true)}>Load new Jokes</Button>
            <Button className={"fetch-button"} onClick={getRandomJoke}>Get a new Joke</Button>
            <Modal show={modal} onHide={() => setModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Get new Jokes</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <>
                            Categories:
                            <ToggleButtonGroup type="checkbox" value={categories}
                                               onChange={(value) => handleCategoriesChanged(value)}>
                                <ToggleButton id="tbg-btn-1-ct" value={"Programming"}>
                                    Programming
                                </ToggleButton>
                                <ToggleButton id="tbg-btn-2-ct" value={"Misc"}>
                                    Misc
                                </ToggleButton>
                                <ToggleButton id="tbg-btn-3-ct" value={"Dark"}>
                                    Dark
                                </ToggleButton>
                                <ToggleButton id="tbg-btn-4-ct" value={"Pun"}>
                                    Pun
                                </ToggleButton>
                                <ToggleButton id="tbg-btn-5-ct" value={"Christmas"}>
                                    Christmas
                                </ToggleButton>
                                <ToggleButton id="tbg-btn-6-ct" value={"Spooky"}>
                                    Spooky
                                </ToggleButton>
                            </ToggleButtonGroup>
                        </>
                        <hr/>
                        <>
                            Language:
                            <Dropdown className="d-inline mx-2">
                                <Dropdown.Toggle id="dropdown-autoclose-true">
                                    {lang}
                                </Dropdown.Toggle>
                                <Dropdown.Menu>
                                    <Dropdown.Item onClick={() => setLang("cs")}>Czech</Dropdown.Item>
                                    <Dropdown.Item onClick={() => setLang("de")}>German</Dropdown.Item>
                                    <Dropdown.Item onClick={() => setLang("en")}>English</Dropdown.Item>
                                    <Dropdown.Item onClick={() => setLang("es")}>Spanish</Dropdown.Item>
                                    <Dropdown.Item onClick={() => setLang("fr")}>French</Dropdown.Item>
                                    <Dropdown.Item onClick={() => setLang("pt")}>Portuguese</Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>
                        </>
                        <hr/>
                        <>
                            Flags:
                            <ToggleButtonGroup type="checkbox" value={flags}
                                               onChange={(value) => handleFlagsChanged(value)}>
                                <ToggleButton id="tbg-btn-1-fl" value={"nsfw"}>
                                    NSFW
                                </ToggleButton>
                                <ToggleButton id="tbg-btn-2-fl" value={"religious"}>
                                    Religious
                                </ToggleButton>
                                <ToggleButton id="tbg-btn-3-fl" value={"political"}>
                                    Political
                                </ToggleButton>
                                <ToggleButton id="tbg-btn-4-fl" value={"racist"}>
                                    Racist
                                </ToggleButton>
                                <ToggleButton id="tbg-btn-5fl" value={"sexist"}>
                                    Sexist
                                </ToggleButton>
                                <ToggleButton id="tbg-btn-6-fl" value={"explicit"}>
                                    Explicit
                                </ToggleButton>
                            </ToggleButtonGroup>
                        </>
                        <hr/>
                        <>
                            JokeType: <br/>
                            <ToggleButtonGroup type="checkbox" value={jokeTypes}
                                               onChange={(value) => handleJokeTypesChanged(value)}>
                                <ToggleButton id="tbg-btn-1-jt" value={"single"}>
                                    Single
                                </ToggleButton>
                                <ToggleButton id="tbg-btn-2-jt" value={"twopart"}>
                                    Two-part
                                </ToggleButton>
                            </ToggleButtonGroup>
                        </>
                        <hr/>
                        <>
                            <Form.Group className="mb-3" controlId="searchString">
                                <Form.Label>Search-String:</Form.Label>
                                <Form.Control as="textarea" rows={3}
                                              onChange={(event) => handleSearchStringChanged(event)}/>
                                <Form.Label>Amount (1-10):</Form.Label>
                                <Form.Control type="number" onChange={(event) => handleAmountChanged(event)}/>
                            </Form.Group>
                        </>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setModal(false)}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={getNewJokes}>
                        Load new Jokes
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default App;
