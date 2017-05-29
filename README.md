# AutoNote

A hands free automatic notetaker that creates and saves notes during lectures

The application uses J.A.R.V.I.S speech api by lkuza2 in order to analyze vocal volume and language. 

J.A.R.V.I.S. Speech API is designed to be simple and efficient, using the speech engines created by Google to provide functionality for parts of the API. Essentially, it is an API written in Java, including a recognizer, synthesizer, and a microphone capture utility.

The Microphone Capture API records from microphone input and converts speech from WAVE files to FLAC using a java flac encoder API.The FLAC file is then sent to Google's speech synthesiser service, which provides a confidence score and text. If spoken in different languages, the application uses Skylion's Google toolkit to translate text to the desired language. 

The raw text is then displayed line by line to the user, and is saved to a txt file using an audio and keyword based formatting algorithm. After recording, users can send and receive files through a built in networking gui panel. 

To set up and use this application, simply clone this repo and run the "MainSpeech.java" file. All of the necessary API's are included. You will have to obtain access to a new Google API Key, instructions to do so can be found in the ##Notes section below.Currently, the application only formats correctly with US History and Science based subjects, but more subjects can be easily added if needed. 

##Notes

To get access to the Google API, you need an API key. To get this, you need to follow the instructions here:

https://stackoverflow.com/questions/26485531/google-speech-api-v2
