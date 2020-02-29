$(document)
    .ready(function() {
        $('.ui.form')
        .form({
            fields: {
                houseName: {
                    rules: [
                        {
                            type: 'empty',
                            prompt: 'Podaj nazwę domu'
                        }
                    ]
                },
                inmateName: {
                    rules: [
                        {
                            type: 'empty',
                            prompt: 'Podaj nazwę domownika'
                        }
                    ]
                },
                housePassword: {
                    rules: [
                        {
                            type: 'minLength[8]',
                            prompt: 'Hasło musi mieć co najmniej 8 znaków'
                        }
                    ]    
                },
                confirmHousePassword: {
                    rules: [
                        {
                            type: 'match[housePassword]',
                            prompt: 'Hasła dla domu nie są takie same'
                        }
                    ]
                },
                inmatePassword: {
                    rules: [
                        {
                            type: 'minLength[8]',
                            prompt: 'Hasło musi mieć co najmniej 8 znaków'
                        }
                    ]
                },
                confirmInmatePassword: {
                    rules: [
                        {
                            type: 'match[inmatePassword]',
                            prompt: 'Hasła dla domownika nie są takie same'
                        }
                    ]
                }
            }
        });
    });
